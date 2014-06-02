package file;

import file.parser.KascadeParser;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class KascadeFileTest {

    @Test
    public void testUpdateBlocks() throws Exception {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();
        KascadeFile file = files.get(0);

        String hexBlocks = file.getBlocks();

        System.out.println(hexBlocks);

        assertEquals(hexBlocks.length(), (int)Math.ceil(file.getBlockhashes().length / 8.0)*2);
    }

    @Test
    public void testAssemble() throws IOException {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
        ArrayList<KascadeFile> files = kascadeParser.getFiles();
        KascadeFile file = files.get(0);

        boolean isComplete = file.assemble();

        assertEquals(true, isComplete);
    }
}