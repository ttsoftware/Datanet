package file.parser;

import org.junit.Test;

import java.io.File;


public class KascadeParserTest {

    @Test
    public void testKascadeFileArray() {

        KascadeParser kascadeReader = new KascadeParser();

        File[] arrayKascade = kascadeReader.arrayOfKascadeFiles("/var/www/shared/kascades");

        for(File file : arrayKascade) {
            System.out.println(file.getName());
        }
    }
}
