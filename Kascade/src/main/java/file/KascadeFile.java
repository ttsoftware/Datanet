package file;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class KascadeFile {

    private String tracker;
    private String trackhash;

    private String filepath;
    private String filename;
    private String filehash;
    private int filesize;

    private String blocks = "";
    private int blocksize;
    private String[] blockhashes;

    public KascadeFile() {
    }

    public KascadeFile(String tracker, String filepath, String filename, String filehash, int filesize, int blocksize) {
        this.tracker = tracker;
        this.filepath = filepath;
        this.filename = filename;
        this.filehash = filehash;
        this.filesize = filesize;
        this.blocksize = blocksize;
    }

    /**
     * If true is returned, the file was succesfully assembled.
     * If false is returned, we do not have all the nessescary blocks.
     *
     * @return boolean
     * @throws IOException
     */
    public boolean assemble() throws IOException {

        String[] blockhashes = getBlockhashes();
        String binaryBlocks = getBinaryBlocks();

        boolean isComplete = true;
        for (char blockExists : binaryBlocks.toCharArray()) {
            if (blockExists == '0') {
                isComplete = false;
            }
        }

        if (isComplete) {

            FileOutputStream outputStream = new FileOutputStream("/var/www/shared/files/" + getFilename());
            // we now want to assemble the file
            for (String blockhash : blockhashes) {

                File blockfile = new File("/var/www/shared/hashes/" + getTrackhash() + "/" + blockhash);
                if (blockfile.exists()) {
                    FileInputStream inputStream = new FileInputStream(blockfile);
                    byte[] blockByteArray = IOUtils.toByteArray(inputStream);
                    outputStream.write(blockByteArray);
                }
                else {
                    return false;
                }
            }
            outputStream.close();
        }

        return isComplete;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public String getTrackhash() {
        return trackhash;
    }

    public void setTrackhash(String trackhash) {
        this.trackhash = trackhash;
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

    public String getFilehash() {
        return filehash;
    }

    public void setFilehash(String filehash) {
        this.filehash = filehash;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getBinaryBlocks() {
        int blockcount = getBlockhashes().length;

        File[] blockfiles = new File("/var/www/shared/hashes/" + getTrackhash()).listFiles();

        String blocks = "";
        if (blockfiles != null && blockfiles.length > 0) {
            for (String blockhash : blockhashes) {
                blocks += "0";
                for (File blockfile : blockfiles) {
                    if (blockfile.getName().equals(blockhash)) {
                        blocks = blocks.substring(0, blocks.length() - 1);
                        blocks += "1";
                    }
                }
            }
        }

        return blocks;
    }

    public String getBlocks() {

        int blockcount = getBlockhashes().length;
        int bytecount = (int) Math.ceil(blockcount / 8.0);

        File[] blockfiles = new File("/var/www/shared/hashes/" + getTrackhash()).listFiles();

        String[] blocks = new String[blockcount];
        for (int i = 0; i < blockcount; i++) {
            blocks[i] = "0";
        }

        if (blockfiles != null && blockfiles.length > 0) {
            for (int i = 0; i < blockhashes.length; i++) {
                for (File blockfile : blockfiles) {
                    if (blockfile.getName().equals(blockhashes[i])) {
                        blocks[i] = "1";
                    }
                }
            }
        }

        String hexBlocks = "";
        for (int i = 0; i < bytecount; i++) {
            String blockString = "";
            for (int j = 0; j < 8; j++) {
                blockString += blocks[i + j];
            }

            String hexVal = Integer.toHexString(Integer.valueOf(blockString, 2));
            if (hexVal.length() == 1) {
                hexVal = "0" + hexVal;
            }

            hexBlocks += hexVal;
        }

        return hexBlocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public void setBlocksize(int blocksize) {
        this.blocksize = blocksize;
    }

    public String[] getBlockhashes() {
        return blockhashes;
    }

    public void setBlockhashes(String[] blockhashes) {
        this.blockhashes = blockhashes;
    }
}
