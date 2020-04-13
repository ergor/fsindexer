package no.netb.magnetar;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import no.netb.magnetar.repository.Repository;
import no.netb.magnetar.web.constants.HttpHeader;
import no.netb.magnetar.web.constants.HttpStatus;
import no.netb.magnetar.web.app.MagnetarWebapp;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.constants.MimeType;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain implements Runnable{

    private static final Logger LOG = Logger.getLogger(ServerMain.class.getName());

    private final Repository repository;

    public ServerMain(Repository repository) {
        this.repository = repository;
    }

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

        server.createContext("/", new MagnetarServer(webapp, repository));
        server.setExecutor(null); // run on main thread
        LOG.info("localhost:" + port + " running...");
        server.start();
    }

    static class MagnetarServer implements HttpHandler {

        private final MagnetarWebapp webapp;
        private final Repository repository;

        public MagnetarServer(MagnetarWebapp webapp, Repository repository) {
            this.webapp = webapp;
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Response response = webapp.resolveControllerByURL(httpExchange.getRequestURI().toString())
                    .map(controller -> controller.applyTemplate(httpExchange, repository))
                    .orElseGet(() -> webapp.getFaultController().handleError(HttpStatus.HTTP_404, httpExchange, null));

            Headers responseHeaders = httpExchange.getResponseHeaders();
            OutputStream os = httpExchange.getResponseBody();
            int responseLength = -1;

            if (response.isRedirect()) {
                responseHeaders.add(HttpHeader.LOCATION.string, response.getLocation());
            } else {
                responseHeaders.add(HttpHeader.CONTENT_TYPE.string, MimeType.TEXT__HTML.string);
                responseLength = response.getResponseLength();
            }

            httpExchange.sendResponseHeaders(response.getHttpStatus().getCode(), responseLength);
            if (responseLength > -1) {
                os.write(response.getHtml().getBytes());
            }
            os.close();
        }
    }
}
