package entities;

public class Prop {

    String getlastmodified;
    ResourceType resourcetype;
    String filename;
    String filetype;
    String filesize;

    @Override
    public String toString() {
        return filename + ":" + filesize;
    }

    public String getGetlastmodified() {
        return getlastmodified;
    }

    public void setGetlastmodified(String getlastmodified) {
        this.getlastmodified = getlastmodified;
    }

    public ResourceType getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(ResourceType resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
}
