package file.parser;

import org.junit.Test;

import java.io.File;


public class KascadeParserTest {

    @Test
    public void testKascadeFileArray() {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");

        File[] actualKascades = kascadeParser.arrayOfKascadeFiles();

    }

    @Test
    public void testKascadeConversion() {

        KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");

    }
}
