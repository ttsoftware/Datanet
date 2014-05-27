package file;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class KascadeFile {

    private String trackerUrl;

    private String filepath;
    private String filename;
    private String filehash;
    private int filesize;

    private String blocks = "";
    private int blocksize;
    private String[] blockhashes;

    public KascadeFile(String trackerUrl, String filepath, String filename, String filehash, int filesize, int blocksize) {
        this.trackerUrl = trackerUrl;
        this.filepath = filepath;
        this.filename = filename;
        this.filehash = filehash;
        this.filesize = filesize;
        this.blocksize = blocksize;

        for (int i = 0; i < (((filesize/blocksize) + 7)/8)*2; i++) {
            this.blocks += "0";
        }
    }

    public String updateBlocks() throws UnsupportedEncodingException {

        String blocks = "";
        blocks = String.format("%040x", new BigInteger(1, blocks.getBytes("UTF-8")));

        setBlocks(blocks);
        return getBlocks();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getFilehash() {
        return filehash;
    }

    public void setFilehash(String filehash) {
        this.filehash = filehash;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public void setBlocksize(int blocksize) {
        this.blocksize = blocksize;
    }

    public String getTrackerUrl() {
        return trackerUrl;
    }

    public void setTrackerUrl(String trackerUrl) {
        this.trackerUrl = trackerUrl;
    }

    public String[] getBlockhashes() {
        return blockhashes;
    }

    public void setBlockhashes(String[] blockhashes) {
        this.blockhashes = blockhashes;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }
}
