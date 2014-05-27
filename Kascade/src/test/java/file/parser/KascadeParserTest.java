package file.parser;

import file.KascadeFile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KascadeParserTest {

    @Test
    public void testKascadeFileArray() {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");

        ArrayList<File> actualKascades = kascadeParser.arrayOfKascadeFiles();

    }

    @Test
    public void testKascadeConversion() throws IOException {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");

        ArrayList<KascadeFile> files = kascadeParser.getFiles();

        for (KascadeFile file : files) {
            System.out.println(file.getFilehash());
        }
    }
}
