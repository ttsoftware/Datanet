package protocol;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import file.KascadeFile;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class TrackerService {

    private int port;

    public TrackerService(int port) {
        this.port = port;
    }

    public String getKascadeJSON(KascadeFile kascadeFile) throws UnsupportedEncodingException {

        Client client = Client.create();

        String blocks = "";
        for (int i = 0; i != 86; i++) {
            blocks += "0";
        }

        String input = "port=6666&blocks=" + blocks;

        WebResource webResource = client.resource(kascadeFile.getTrackerUrl());
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, input);

        Map headers = response.getHeaders();

        System.out.println(response.getStatus());

        for (Object header : headers.keySet()) {
            System.out.println(header + " : " + headers.get(header));
        }

        return response.getEntity(String.class);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
