package file.converter;

import java.io.*;
import java.util.*;
/**
 * Created by Troels L. & Allan on 5/26/14.
 */
public class KascadeParserService {

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
