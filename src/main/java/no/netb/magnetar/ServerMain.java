package no.netb.magnetar;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
        server.createContext("/test", new MagnetarServer());
        server.setExecutor(null); // run on main thread
        LOG.info("localhost:" + port + " running...");
        server.start();
    }

    static class MagnetarServer implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "This is the response";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
