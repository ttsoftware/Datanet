package server;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by arahlon on 6/7/14.
 */
@WebService()
public class HttpServer {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new HttpServer ();
    String address = "http://localhost:9000/HttpServer";
    Endpoint.publish(address, implementor);
  }
}
