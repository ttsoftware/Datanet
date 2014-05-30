package protocol;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;

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
}
