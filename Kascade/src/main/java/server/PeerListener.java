package server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.net.URISyntaxException;

public class PeerListener implements Runnable {

    private int port;

    public PeerListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        // http://localhost:6666/4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942
        try {
            ResourceConfig rc = new ResourceConfig().register(PeerServlet.class);
            HttpServer server = JdkHttpServerFactory.createHttpServer(new URI("http://127.0.0.1:" + port + "/"), rc);
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
