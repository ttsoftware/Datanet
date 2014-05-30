package servlet;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class PeerListener implements Runnable {

    private int port;

    public PeerListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        // http://localhost:9998/4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942

        HttpServer server = null;
        try {
            server = HttpServerFactory.create("http://127.0.0.1:" + port + "/");
            server.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
