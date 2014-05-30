import file.KascadeFile;
import file.parser.KascadeParser;
import protocol.Peer;
import servlet.PeerListener;
import protocol.PeerService;
import protocol.TrackerService;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

    static int port = 6666;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {

        Thread listeningThread = new Thread(new PeerListener(port));
        listeningThread.start();

        download();
    }

    public static void download() throws IOException, NoSuchAlgorithmException, InterruptedException {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();

        for (KascadeFile file : files) {

            File directory = new File("/var/www/shared/hashes/" + file.getTrackhash());

            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    continue; // unable to create directory for blocks
                }
            }

            TrackerService trackerService = new TrackerService(port);
            ArrayList<Peer> peers = trackerService.getPeers(file);

            PeerService peerService = new PeerService();
            peerService.downloadBlocks(peers, file);
            boolean success = file.assemble();

            if (success) {
                System.out.println("File " + file.getFilename() + " succesfully downloaded.");
            }
        }
    }
}
