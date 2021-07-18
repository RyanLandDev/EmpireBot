package net.ryanland.empire.sys.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.ryanland.empire.sys.externalfiles.ExternalFiles;

import java.io.IOException;
import java.nio.file.Files;

public class ConfigHandler {

    private Config CONFIG;

    public static Config loadConfig() throws IOException {
        String content = ExternalFiles.CONFIG.getContent();
        JsonObject rawJson = JsonParser.parseString(content).getAsJsonObject();
        return new Config(rawJson);
    }

    public Config getConfig() {
        return CONFIG;
    }

}
