package servlet;

public class PeerListener implements Runnable {

    private int port;

    public PeerListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {


    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
