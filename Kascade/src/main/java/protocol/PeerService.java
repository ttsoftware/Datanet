package protocol;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import file.KascadeFile;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PeerService {

    public boolean validateBlock(byte[] blockcontent, String blockhash) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashbytes = md.digest(blockcontent);

        BigInteger hashBits = new BigInteger(1, hashbytes);
        String hashtext = hashBits.toString(16);
        while (hashtext.length() < 32) { // prefix with 0's to ensure correct length
            hashtext = "0" + hashtext;
        }

        return blockhash.equals(hashtext);
    }

    public void downloadBlocks(ArrayList<Peer> peers, KascadeFile file) throws IOException, NoSuchAlgorithmException {

        for (Peer peer : peers) {
            HashMap<String, Integer> peerBlockStartingBytes = peer.decodeBlocks(file);

            System.out.println("Trying " + peerBlockStartingBytes.size() + " blocks from peer " + peer.getIp() + ":" + peer.getPort());

            for (Map.Entry<String, Integer> blockEntry : peerBlockStartingBytes.entrySet()) {

                byte[] blockContent = downloadBlock(peer, file, blockEntry.getValue());

                if (blockContent.length > 0
                    && validateBlock(blockContent, blockEntry.getKey())) {

                    System.out.println("\tRecieved block " + blockEntry.getKey());

                    FileOutputStream outputStream = new FileOutputStream("/var/www/shared/hashes/" + file.getTrackhash() + "/" + blockEntry.getKey());
                    outputStream.write(blockContent);
                    outputStream.close();
                }
            }
        }
    }

    public byte[] downloadBlock(Peer peer, KascadeFile file, int startByte) throws IOException {

        String resource = "http://" + peer.getIp() + ":" + peer.getPort() + "/" + file.getTrackhash();

        Client client = Client.create();
        WebResource webResource = client.resource(resource);

        int endByte = startByte + file.getBlocksize() - 1;
        if (endByte >= file.getFilesize()) {
            endByte = file.getFilesize() - 1;
        }

        ClientResponse response;
        try {
            response = webResource
                    .header("Range", "bytes=" + Integer.toString(startByte) + "-" + Integer.toString(endByte))
                    .type("text/plain")
                    .get(ClientResponse.class);
        }
        catch (ClientHandlerException e) {
            if (e.getCause().getMessage().equals("Connection timed out")) {
                return new byte[]{}; // try next block/peer.
            }
            e.printStackTrace();
            throw new RuntimeException("\nRange: " + Integer.toString(startByte) + "-" + Integer.toString(endByte) + "\nGET " + resource + " HTTP 1.1");
        }

        InputStream inputStream = response.getEntityInputStream();
        byte[] blockByteArray = IOUtils.toByteArray(inputStream);

        Map headers = response.getHeaders();
        String headerString = "";
        for (Object header : headers.keySet()) {
            headerString += "\n" + header + " : " + headers.get(header);
        }

        switch (response.getStatus()) {
            case 206:
                // recieved block succesfully.
                return blockByteArray;
            default:
                throw new RuntimeException("\nRange: " + Integer.toString(startByte) + "-" + Integer.toString(endByte) +
                                           "\nGET " + resource + " HTTP 1.1: " +
                                           response.getStatus() + headerString);
        }
    }
}
