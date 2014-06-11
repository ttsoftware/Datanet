import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {

        URI uri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig rc = new ResourceConfig()
                .packages("protocol")
                .registerInstances(new LoggingFilter(Logger.getLogger(Main.class.getName()), true));

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, rc);
        System.in.read();
    }
}
