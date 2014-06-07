package client;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import file.KascadeFile;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TrackerService {

    private int port;

    public TrackerService(int port) {
        this.port = port;
    }

    public ArrayList<Peer> getPeers(KascadeFile kascadeFile) throws IOException, InterruptedException {

        Client client = ClientBuilder.newClient();

        Response response = client.target(kascadeFile.getTracker())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(
                              new Form()
                                      .param("port", Integer.toString(getPort()))
                                      .param("blocks", kascadeFile.getBlocks()),
                              MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                );

        String responseBody = response.readEntity(String.class);

        Map headers = response.getHeaders();
        String headerString = "";
        for (Object header : headers.keySet()) {
            headerString += "\n" + header + " : " + headers.get(header);
        }

        switch (response.getStatus()) {
            case 403:
                sleep(60);
                break;
            case 200:
                break;
            default:
                throw new RuntimeException("\nHTTP status: " + response.getStatus() + "\n" + headerString + "\n" + responseBody);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(responseBody, new TypeReference<List<Peer>>(){});
        }
        catch (JsonParseException e) {
            System.out.println(responseBody);
            throw e;
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
