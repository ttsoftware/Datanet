package protocol;

import file.KascadeFile;
import org.junit.Test;

import java.util.ArrayList;

public class TrackerServiceTest {

    @Test
    public void testGetPeers() throws Exception {

        int port = 6666;
        String host = "http://datanet2014tracker.appspot.com/peers/";
        String resource = "4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942.json";
        String resourcePaths = "/var/www/shared/files";

        KascadeFile file = new KascadeFile(
            host + resource,
            resourcePaths,
            "bbb_sunflower_1080p_60fps_normal.mp4",
            "4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942",
            355856562,
            1048576
        );

        TrackerService trackerService = new TrackerService(port);
        ArrayList<Peer> peers = trackerService.getPeers(file);

        PeerService peerService = new PeerService();

        for (Peer peer : peers) {
            System.out.println(peer.getIp());
            System.out.println(peer.getPort());
            System.out.println(peer.getUpdated());
            System.out.println(peer.getBlocks());
            System.out.println(peer.isFeeder());

            if (!peer.getBlocks().equals("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000")) {
                String blockContent = peerService.getBlock(peer, file, "5248e47bd387f5fa0d4ffebaae554207");
                System.out.println(blockContent);
                break;
            }
        }
    }
}