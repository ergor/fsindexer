package no.netb.magnetar;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerMain implements Runnable{

    @Override
    public void run() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            System.out.println("magnetar: failed to start webserver");
            e.printStackTrace();
            System.exit(ExitCode.INTERNAL_ERROR.value);
        }
        server.createContext("/test", new MagnetarServer());
        server.setExecutor(null); // creates a default executor
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
