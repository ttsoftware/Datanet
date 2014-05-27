package protocol;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Peer {

    private String ip;
    private String updated;
    private String blocks;
    private int port;
    private boolean feeder;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isFeeder() {
        return feeder;
    }

    public void setFeeder(boolean feeder) {
        this.feeder = feeder;
    }
}
