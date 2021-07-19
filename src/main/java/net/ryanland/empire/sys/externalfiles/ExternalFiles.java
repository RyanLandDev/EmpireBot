package net.ryanland.empire.sys.externalfiles;

public interface ExternalFiles {

    ExternalFile CONFIG = new ExternalFileBuilder()
            .setName("config")
            .setFileType(ExternalFileType.JSON)
            .setDefaultContent("{\n" +
                    "  \"token\": \"\",\n" +
                    "  \"client_id\": \"\",\n" +
                    "  \"prefix\": \"\",\n" +
                    "\n" +
                    "  \"database_uri\": \"\"\n" +
                    "}")
            .buildFile();

    ExternalFile RANKS = new ExternalFileBuilder()
            .setName("ranks")
            .setFileType(ExternalFileType.JSON)
            .buildFile();

}
