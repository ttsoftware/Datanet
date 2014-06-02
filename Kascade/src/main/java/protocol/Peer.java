package protocol;

import file.KascadeFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Peer {

    private String ip;
    private String updated;
    private String blocks;
    private int port;
    private boolean feeder;

    /**
     * Returns the starting bytes in the byte ranges we want to download.
     *
     * @param file
     * @return ArrayList
     */
    public HashMap<String, Integer> decodeBlocks(KascadeFile file) {

        int blockcount = file.getBlockhashes().length;
        String blocks = getBlocks();

        String blockString = "";
        for (int i = 0; i < blocks.length(); i += 2) {
            String blockByte = blocks.substring(i, i+2);
            String byteBlock = Integer.toBinaryString(Integer.valueOf(blockByte, 16));

            if (byteBlock.equals("0")) {
                blockString += "00000000";
            }
            else {
                blockString += byteBlock;
            }
        }

        if (blockString.length() > blockcount) {
            blockString = blockString.substring(0, blockcount); // remove trailing 0's
        }
        else {
            // append trailing 0's
            while (blockString.length() < blockcount) {
                blockString += "0";
            }
        }

        char[] peerBlocks = blockString.toCharArray();
        char[] existingBlocks = file.getBinaryBlocks().toCharArray();

        HashMap<String, Integer> startingBytes = new HashMap<String, Integer>();
        for (int i = 0; i < blockcount; i++) {
            if (peerBlocks[i] == '1' && existingBlocks.length == 0) {
                startingBytes.put(file.getBlockhashes()[i], i * file.getBlocksize());
            }
            else if (peerBlocks[i] == '1' && existingBlocks[i] == '0') {
                // we should request this block.
                // this block must be located at i * blocksize
                startingBytes.put(file.getBlockhashes()[i], i * file.getBlocksize());
            }
        }

        return startingBytes;
    }

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
