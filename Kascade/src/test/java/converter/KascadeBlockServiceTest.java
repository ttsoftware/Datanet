package converter;

import file.converter.KascadeParserService;
import org.junit.Test;

import java.io.File;

/**
 * Created by arahlon on 5/26/14.
 */
public class KascadeBlockServiceTest {

    @Test
    public void testKascadeFileArray() {

        KascadeParserService kascadeReader = new KascadeParserService();

        File[] arrayKascade = kascadeReader.arrayOfKascadeFiles("/var/home/kascades");
        System.out.println(" We begin \n");

        for(File kascFile : arrayKascade) {
            System.out.println(kascFile.getName());
        }
        System.out.println("We finish \n");

    }


}
