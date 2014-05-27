package converter;

import file.converter.KascadeParserService;
import org.junit.Test;

import java.io.File;

public class KascadeBlockServiceTest {

    @Test
    public void testKascadeFileArray() {

        KascadeParserService kascadeParser = new KascadeParserService();

        File[] actualKascades = kascadeParser.arrayOfKascadeFiles("/var/www/kascades");
        File[] expectedKascades = kascadeParser.arrayOfKascadeFiles("/home/arahlon/Desktop/Uni/Datanet/Kascade/src/test/java/testFiles/kascadeExpected");

        for(int i = 0; i == actualKascades.length; i++) {
            assert (expectedKascades[i].getName() == actualKascades[i].getName());
        }

    }

    @Test
    public void testKascadeConversion() {

        KascadeParserService kascadeParser = new KascadeParserService();

    }

}
