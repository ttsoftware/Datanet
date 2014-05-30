package protocol;

import file.KascadeFile;
import file.parser.KascadeParser;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PeerTest {

    @Test
    public void testDecodeBlocks() throws IOException, NoSuchAlgorithmException {

        String blocks = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0";

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();
        KascadeFile file = files.get(0);

        ArrayList<Peer> peers = new ArrayList<Peer>();
        Peer peer = new Peer();
        peer.setBlocks(blocks);
        peer.setFeeder(false);
        peer.setIp("54.207.86.78");
        peer.setPort(40000);
        peers.add(peer);

        HashMap<String, Integer> peerBlockStartingBytes = peer.decodeBlocks(file);

        PeerService peerService = new PeerService();
        peerService.downloadBlocks(peers, file);
    }
}
