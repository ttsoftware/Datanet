package protocol;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import file.KascadeFile;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

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

        String input = "port=" + Integer.toString(getPort()) + "&blocks=" + kascadeFile.getBlocks();

        Client client = Client.create();

        WebResource webResource = client.resource(kascadeFile.getTracker());
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, input);

        String responseBody = response.getEntity(String.class);

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
                throw new RuntimeException("\nInput: " + input + "\nHTTP status: " + response.getStatus() + "\n" + headerString + "\n" + responseBody);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody, new TypeReference<List<Peer>>(){});
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
