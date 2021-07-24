package net.ryanland.empire.sys.externalfiles;

public interface ExternalFiles {

    ExternalFile CONFIG = new ExternalFileBuilder()
            .setName("config")
            .setFileType(ExternalFileType.JSON)
            .setDefaultContent("""
                    {
                      "token": "",
                      "client_id": "",
                      "prefix": "",

                      "database_uri": ""
                    }""")
            .buildFile();

    ExternalFile RANKS = new ExternalFileBuilder()
            .setName("ranks")
            .setFileType(ExternalFileType.JSON)
            .buildFile();

    ExternalFile WEBHOOKS = new ExternalFileBuilder()
            .setName("webhooks")
            .setFileType(ExternalFileType.JSON)
            .setDefaultContent("""
                    {
                        "guild_traffic": ""
                    }""")
            .buildFile();
}
