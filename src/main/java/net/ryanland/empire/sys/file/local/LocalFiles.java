package net.ryanland.empire.sys.file.local;

public interface LocalFiles {

    LocalFile CONFIG = new LocalFileBuilder()
        .setName("config")
        .setFileType(LocalFileType.JSON)
        .setDefaultContent("""
            {
              "token": "",
              "client_id": "",

              "database_uri": ""
            }""")
        .buildFile();

    LocalFile RANKS = new LocalFileBuilder()
        .setName("ranks")
        .setFileType(LocalFileType.JSON)
        .buildFile();

    LocalFile WEBHOOKS = new LocalFileBuilder()
        .setName("webhooks")
        .setFileType(LocalFileType.JSON)
        .setDefaultContent("""
            {
                "guild_traffic": ""
            }""")
        .buildFile();
}
