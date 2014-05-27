package protocol;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import file.KascadeFile;

import java.util.Map;

public class PeerService {

    public String getBlock(Peer peer, KascadeFile file, String blockhash) {

        String resource = "http://" + peer.getIp() + ":" + peer.getPort() + "/" + file.getFilehash();

        Client client = Client.create();
        WebResource webResource = client.resource(resource);

        int range = file.getBlocksize() - 1;
        ClientResponse response = webResource
                .header("Range", "bytes=0-" + Integer.toString(range))
                .type("text/plain")
                .get(ClientResponse.class);

        System.out.println(range);

        String responseBody = response.getEntity(String.class);

        Map headers = response.getHeaders();
        String headerString = "";
        for (Object header : headers.keySet()) {
            headerString += "\n" + header + " : " + headers.get(header);
        }

        if (response.getStatus() != 200) {
            throw new RuntimeException("\nGET " + resource + " HTTP 1.1: " + response.getStatus() + headerString + "\n" + responseBody);
        }

        return responseBody;
    }
}
