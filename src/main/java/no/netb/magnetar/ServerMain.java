package no.netb.magnetar;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import no.netb.libjcommon.StreamUtil;
import no.netb.magnetar.repository.Repository;
import no.netb.magnetar.web.constants.HttpHeader;
import no.netb.magnetar.web.constants.HttpStatus;
import no.netb.magnetar.web.app.MagnetarWebapp;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.constants.MimeType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
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
            String requestURI = httpExchange.getRequestURI().toString();
            if (requestURI.startsWith("/style")
                    || requestURI.startsWith("/favicon")) {
                handleFiltered(httpExchange);
            } else {
                handleWebapp(httpExchange);
            }
        }

        private void handleWebapp(HttpExchange httpExchange) throws IOException {
            Response response = webapp.resolveControllerByURL(httpExchange.getRequestURI().toString())
                    .map(controller -> controller.applyTemplate(httpExchange, repository))
                    .orElseGet(() -> webapp.getFaultController().notFound(httpExchange));

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

        private void handleFiltered(HttpExchange httpExchange) throws IOException {
            String requestUrl = httpExchange.getRequestURI().toString();
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(requestUrl);

            Headers responseHeaders = httpExchange.getResponseHeaders();
            OutputStream os = httpExchange.getResponseBody();
            byte[] bytes;

            if (is == null) {
                Response response = webapp.getFaultController().notFound(httpExchange);
                bytes = response.getHtml().getBytes();

                responseHeaders.add(HttpHeader.CONTENT_TYPE.string, MimeType.TEXT__HTML.string);
                httpExchange.sendResponseHeaders(response.getHttpStatus().getCode(), bytes.length);
            }
            else if (requestUrl.startsWith("/style")) {
                String data = StreamUtil.writeToString(is, StandardCharsets.UTF_8);
                bytes = data.getBytes();

                responseHeaders.add(HttpHeader.CONTENT_TYPE.string, MimeType.TEXT__CSS.string);
                httpExchange.sendResponseHeaders(HttpStatus.HTTP_200.getCode(), bytes.length);
            }
            else {
                bytes = StreamUtil.writeToByteArray(is);

                responseHeaders.add(HttpHeader.CONTENT_TYPE.string, MimeType.APPLICATION__OCTET_STREAM.string);
                httpExchange.sendResponseHeaders(HttpStatus.HTTP_200.getCode(), bytes.length);
            }
            os.write(bytes);
            os.close();
        }
    }
}
