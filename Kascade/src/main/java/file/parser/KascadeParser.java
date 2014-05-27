package file.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class KascadeParser {

    public File[] arrayOfKascadeFiles(String path) {
        List<String> results = new ArrayList<String>();
        File[] files = new File(path).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }

        return files;
    }
}
