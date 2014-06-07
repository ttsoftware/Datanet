package client;

import file.KascadeFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PeerClient implements Runnable {

    private int port;
    private KascadeFile file;

    @Override
    public void run() {

        try {
            TrackerService trackerService = new TrackerService(getPort());
            ArrayList<Peer> peers = trackerService.getPeers(getFile());

            System.out.println("Found " + peers.size() + " peers.");

            PeerService peerService = new PeerService();
            peerService.downloadBlocks(peers, getFile());

            boolean success = file.assemble();
            if (success) {
                Timestamp time = new Timestamp(new Date().getTime());
                System.out.println(time + ": File " + file.getFilename() + " succesfully downloaded.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PeerClient(int port, KascadeFile file) {
        this.port = port;
        this.file = file;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public KascadeFile getFile() {
        return file;
    }

    public void setFile(KascadeFile file) {
        this.file = file;
    }
}
