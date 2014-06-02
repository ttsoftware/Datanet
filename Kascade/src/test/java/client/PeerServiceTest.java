package client;

import file.KascadeFile;
import file.parser.KascadeParser;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PeerServiceTest {

    @Test
    public void testValidateBlock() throws IOException, NoSuchAlgorithmException {

        String blockname = "0b642b1ec18ff6f742e662aa3d2f1217";
        File blockfile = new File("/var/www/shared/hashes/4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942/" + blockname);

        FileInputStream inputStream = new FileInputStream(blockfile);
        byte[] blockByteArray = IOUtils.toByteArray(inputStream);

        PeerService peerService = new PeerService();
        boolean isCompleted = peerService.validateBlock(blockByteArray, blockname);

        assertEquals(true, isCompleted);
    }

    @Test
    public void testDownloadBlock() throws IOException, InterruptedException, NoSuchAlgorithmException {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();
        KascadeFile file = files.get(0);

        Peer peer = new Peer();
        peer.setPort(6666);
        peer.setIp("127.0.0.1");

        PeerService peerService = new PeerService();
        byte[] blockBytes = peerService.downloadBlock(peer, file, 0);

        System.out.println(Arrays.toString(blockBytes));
    }
}
