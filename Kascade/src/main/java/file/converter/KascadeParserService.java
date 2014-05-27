package file.converter;

import file.KascadeFile;

import java.io.*;
import java.util.*;

public class KascadeParserService {

    private String path;

    public KascadeParserService(String path) {
        this.path = path;
    }

    public File[] arrayOfKascadeFiles() {
        List<String> results = new ArrayList<String>();
        File[] files = new File(this.path).listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".kascade")) {
                results.add(file.getName());
            }
        }

        return files;
    }

    public void fileToKascade(String fileName) {
        for (File file : arrayOfKascadeFiles()) {
            BufferedReader reader = new BufferedReader(new FileReader(this.path + file.getName()));

            String trackerUrl = null;
            String filepath = null;
            String filename = null;
            String filehash = null;
            int filesize = 0;
            int blocksize = 0;

            String line = null;

            KascadeFile kascFile = new KascadeFile(trackerUrl,
                                                   filepath,
                                                   filename,
                                                   filehash,
                                                   filesize,
                                                   blocksize);
        }
    }


}
