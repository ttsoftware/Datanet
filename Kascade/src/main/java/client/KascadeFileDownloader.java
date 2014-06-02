package client;

import file.KascadeFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class KascadeFileDownloader implements Runnable {

    private ArrayList<Peer> peers;
    private KascadeFile file;

    @Override
    public void run() {
        PeerService peerService = new PeerService();
        try {
            peerService.downloadBlocks(getPeers(), getFile());
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

    public KascadeFileDownloader(ArrayList<Peer> peers, KascadeFile file) {
        this.peers = peers;
        this.file = file;
    }

    public ArrayList<Peer> getPeers() {
        return peers;
    }

    public void setPeers(ArrayList<Peer> peers) {
        this.peers = peers;
    }

    public KascadeFile getFile() {
        return file;
    }

    public void setFile(KascadeFile file) {
        this.file = file;
    }
}
