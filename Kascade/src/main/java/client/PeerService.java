package client;

import file.KascadeFile;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

            if (peerBlockStartingBytes.size() > 0) {

                System.out.println("Trying " + peerBlockStartingBytes.size() + " blocks from peer " + peer.getIp() + ":" + peer.getPort());

                for (Map.Entry<String, Integer> blockEntry : peerBlockStartingBytes.entrySet()) {

                    byte[] blockContent = new byte[]{};
                    try {
                        downloadBlock(peer, file, blockEntry.getValue());
                    }
                    catch (ConnectException e) {
                        System.out.println(e.getMessage());
                        break; // try next peer
                    }

                    if (blockContent.length > 0 && validateBlock(blockContent, blockEntry.getKey())) {

                        System.out.println("\tRecieved block " + blockEntry.getKey());

                        FileOutputStream outputStream = new FileOutputStream("/var/www/shared/hashes/" + file.getTrackhash() + "/" + blockEntry.getKey());
                        outputStream.write(blockContent);
                        outputStream.close();
                    }
                }
            }
        }
    }

    public byte[] downloadBlock(Peer peer, KascadeFile file, int startByte) throws IOException {

        String resource = "http://" + peer.getIp() + ":" + peer.getPort() + "/" + file.getTrackhash();

        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 30000);

        int endByte = startByte + file.getBlocksize() - 1;
        if (endByte >= file.getFilesize()) {
            endByte = file.getFilesize() - 1;
        }

        Response response;
        try {
            response = client.target(resource)
                    .request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .header("Range", "bytes=" + Integer.toString(startByte) + "-" + Integer.toString(endByte))
                    .get();
        }
        catch (Exception e) {
            throw new ConnectException("Could not connect to " + resource + "\n\t" + e.getCause().getMessage());
        }

        byte[] blockByteArray = response.readEntity(byte[].class);

        Map headers = response.getHeaders();
        String headerString = "";
        for (Object header : headers.keySet()) {
            headerString += "\n" + header + " : " + headers.get(header);
        }

        switch (response.getStatus()) {
            case 206:
                // recieved block succesfully.
                return blockByteArray;
            case 400:
                // block not found, try next peer.
                return new byte[]{};
            default:
                throw new RuntimeException("\nRange: " + Integer.toString(startByte) + "-" + Integer.toString(endByte) +
                                           "\nGET " + resource + " HTTP 1.1: " +
                                           response.getStatus() + headerString);
        }
    }
}
