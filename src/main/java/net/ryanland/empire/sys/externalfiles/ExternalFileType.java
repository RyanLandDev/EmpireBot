package net.ryanland.empire.sys.externalfiles;

public enum ExternalFileType {
    UNKNOWN("unk"),

    TEXT("txt"),
    JSON("json", "{}");

    private final String extension;
    private final byte[] defaultContent;

    ExternalFileType(String extension, byte[] defaultContent) {
        this.extension = extension;
        this.defaultContent = defaultContent;
    }

    ExternalFileType(String extension, String defaultContent) {
        this.extension = extension;
        this.defaultContent = defaultContent.getBytes();
    }

    ExternalFileType(String extension) {
        this.extension = extension;
        this.defaultContent = null;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getDefaultContent() {
        return defaultContent;
    }
}
