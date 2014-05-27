package file;

import java.util.HashMap;

public class KascadeFile {

    protected String trackerUrl;

    protected String filepath;
    protected String filename;
    protected String filehash;
    protected int filesize;

    protected String blocks = "";
    protected int blocksize;
    protected HashMap<String, Boolean> blockhashes;

    public KascadeFile(String trackerUrl, String filepath, String filename, String filehash, int filesize, int blocksize) {
        this.trackerUrl = trackerUrl;
        this.filepath = filepath;
        this.filename = filename;
        this.filehash = filehash;
        this.filesize = filesize;
        this.blocksize = blocksize;

        this.blockhashes = new HashMap<String, Boolean>();

        for (int i = 0; i < (((filesize/blocksize) + 7)/8)*2; i++) {
            this.blocks += "0";
        }
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

    public HashMap<String, Boolean> getBlockhashes() {
        return blockhashes;
    }

    public void setBlockhashes(HashMap<String, Boolean> blockhashes) {
        this.blockhashes = blockhashes;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }
}
