package no.netb.magnetar;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import no.netb.magnetar.webui.app.HttpStatus;
import no.netb.magnetar.webui.app.MagnetarWebapp;
import no.netb.magnetar.webui.app.Response;
import no.netb.magnetar.webui.controller.FaultController;
import no.netb.magnetar.webui.controller.MagnetarController;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain implements Runnable{

    private static final Logger LOG = Logger.getLogger(ServerMain.class.getName());

    @Override
    public void run() {
        int port = 8000;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "magnetar: failed to start webserver", e);
            System.exit(ExitCode.INTERNAL_ERROR.value);
        }

        MagnetarWebapp webapp = new MagnetarWebapp();

        server.createContext("/", new MagnetarServer(webapp));
        server.setExecutor(null); // run on main thread
        LOG.info("localhost:" + port + " running...");
        server.start();
    }

    static class MagnetarServer implements HttpHandler {

        private final MagnetarWebapp webapp;
        private final TemplateEngine templateEngine;

        public MagnetarServer(MagnetarWebapp webapp) {
            this.webapp = webapp;
            this.templateEngine = webapp.getTemplateEngine();
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Response response = webapp.resolveControllerByURL(httpExchange.getRequestURI().toString())
                    .map(controller -> controller.applyTemplate(httpExchange, templateEngine))
                    .orElseGet(() -> ((FaultController) webapp.getFaultController()).doFault(HttpStatus.HTTP_404, templateEngine));

            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Content-Type", "text/html");
            httpExchange.sendResponseHeaders(response.getHttpStatus().getCode(), response.getResponseLength());

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getHtml().getBytes());
            os.close();
        }
    }
}
