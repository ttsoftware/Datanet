import file.KascadeFile;
import file.parser.KascadeParser;
import client.Peer;
import client.KascadeFileDownloader;
import servlet.PeerListener;
import client.TrackerService;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class
        Main {

    static int port = 6666;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {

        Thread listeningThread = new Thread(new PeerListener(port));
        //listeningThread.start();

        download();
    }

    public static void download() throws IOException, NoSuchAlgorithmException, InterruptedException {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();

        for (KascadeFile file : files) {

            Timestamp time = new Timestamp(new Date().getTime());
            System.out.println(time + ": Downloading file:" + file.getFilename());

            File directory = new File("/var/www/shared/hashes/" + file.getTrackhash());

            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    continue; // unable to create directory for blocks
                }
                if (directory.setReadable(true)) {
                    if (!directory.setWritable(true)) {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }

            TrackerService trackerService = new TrackerService(port);
            ArrayList<Peer> peers = trackerService.getPeers(file);

            System.out.println("Found " + peers.size() + " peers.");

            Thread downloadThread = new Thread(new KascadeFileDownloader(peers, file));
            downloadThread.start();
        }
    }
}
