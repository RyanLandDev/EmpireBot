package net.ryanland.empire.sys.externalfiles;

public interface ExternalFiles {

    ExternalFile CONFIG = new ExternalFileBuilder()
            .setName("config")
            .setFileType("json")
            .buildFile();

}
