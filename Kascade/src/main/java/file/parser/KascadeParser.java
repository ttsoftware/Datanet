package file.parser;

import file.KascadeFile;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import protocol.Peer;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class KascadeParser {

    private String path;

    public KascadeParser(String path) {
        this.path = path;
    }

    public ArrayList<KascadeFile> getFiles() throws IOException {
        ArrayList<KascadeFile> files = new ArrayList<KascadeFile>();

        for (File file : arrayOfKascadeFiles()) {
            files.add(fileToKascade(file.getAbsolutePath()));
        }

        return files;
    }

    public ArrayList<File> arrayOfKascadeFiles() {
        ArrayList<File> results = new ArrayList<File>();
        File[] files = new File(getPath()).listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".kascade")) {
                results.add(file);
            }
        }

        return results;
    }

    public KascadeFile fileToKascade(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filepath), KascadeFile.class);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
