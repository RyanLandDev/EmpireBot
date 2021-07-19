package net.ryanland.empire.sys.externalfiles;

import java.io.File;
import java.util.Arrays;

public class ExternalFileBuilder {

    String fileName;
    ExternalFileType fileType = ExternalFileType.UNKNOWN;
    byte[] defaultContent = null;
    boolean directory = false;

    public ExternalFileBuilder setName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ExternalFileBuilder setFileType(ExternalFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public ExternalFileBuilder setDefaultContent(byte[] defaultContent) {
        this.defaultContent = defaultContent;
        return this;
    }

    public ExternalFileBuilder setDefaultContent(String defaultContent) {
        return setDefaultContent(defaultContent.getBytes());
    }

    public ExternalFileBuilder isDirectory(boolean directory) {
        this.directory = directory;
        return this;
    }

    public ExternalFile buildFile() {
        ExternalFile file = new ExternalFile(fileName + (directory ? "" : "." + fileType.getExtension()));
        try {
            if (!file.exists()) {
                if (directory) {
                    file.mkdir();
                } else {
                    file.createNewFile();
                    if (defaultContent == null) defaultContent = fileType.getDefaultContent();
                    if (defaultContent != null) file.write(defaultContent);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}