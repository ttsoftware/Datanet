package client;

import file.KascadeFile;
import file.parser.KascadeParser;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class IntegrationTest {

    @Test
    public void testGetPeers() throws IOException, NoSuchAlgorithmException, InterruptedException {

        int port = 6666;

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();
        KascadeFile file = files.get(0);

        TrackerService trackerService = new TrackerService(port);
        ArrayList<Peer> peers = trackerService.getPeers(file);

        PeerService peerService = new PeerService();
        peerService.downloadBlocks(peers, file);
        file.assemble();
    }
}