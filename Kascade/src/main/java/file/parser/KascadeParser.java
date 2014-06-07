package file.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import file.KascadeFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KascadeParser {

    private String path;

    public KascadeParser(String path) {
        this.path = path;
    }

    public ArrayList<KascadeFile> getFiles() throws IOException {
        ArrayList<KascadeFile> files = new ArrayList<KascadeFile>();

        for (File file : arrayOfKascadeFiles()) {
            KascadeFile fileObject = fileToKascade(file.getAbsolutePath());
            if (fileObject instanceof KascadeFile) {
                files.add(fileObject);
            }
        }

        return files;
    }

    public ArrayList<File> arrayOfKascadeFiles() {
        ArrayList<File> results = new ArrayList<File>();
        File[] files = new File(getPath()).listFiles();

        for (File file : files) {
            if (file.getName().endsWith(".kascade")) {
                results.add(file);
            }
        }

        return results;
    }

    public KascadeFile fileToKascade(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filepath), KascadeFile.class);
        }
        catch (IOException e) {
            return null;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
