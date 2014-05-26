package protocol;

import file.KascadeFile;
import org.junit.Test;

import java.util.HashMap;

public class TrackerServiceTest {

    @Test
    public void testGetKascade() throws Exception {

        int port = 6666;
        String host = "http://datanet2014tracker.appspot.com/peers/";
        String resource = "4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942.json";
        String resourcePaths = "/var/www/shared/";

        KascadeFile file = new KascadeFile(
            host + resource,
            resourcePaths,
            "bbb_sunflower_1080p_60fps_normal.mp4",
            "4ad2b83af9573f2074e70ead5b23e9dea1786b4293e6726f88b8e34f1b4a8942",
            355856562,
            1048576
        );

        TrackerService trackerService = new TrackerService(port);
        String content = trackerService.getKascadeJSON(file);

        System.out.println(content);
    }
}