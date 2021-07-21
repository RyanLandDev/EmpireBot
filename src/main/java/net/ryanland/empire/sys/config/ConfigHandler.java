package net.ryanland.empire.sys.config;

import com.google.gson.JsonObject;
import net.ryanland.empire.sys.externalfiles.ExternalFiles;

import java.io.IOException;

public class ConfigHandler {

    private Config CONFIG;

    public static Config loadConfig() throws IOException {
        JsonObject rawJson = ExternalFiles.CONFIG.parseJson();
        return new Config(rawJson);
    }

    public Config getConfig() {
        return CONFIG;
    }

}
