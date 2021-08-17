package net.ryanland.empire.sys.file.config;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class Config {

    private final JsonObject rawConfig;

    private final String token;
    private final String clientId;
    private final String databaseURI;

    public Config(@NotNull JsonObject rawConfig) {
        this.rawConfig = rawConfig;

        token = rawConfig.get("token").getAsString();
        clientId = rawConfig.get("client_id").getAsString();
        databaseURI = rawConfig.get("database_uri").getAsString();
    }

    public JsonObject getRawConfig() {
        return rawConfig;
    }

    public String getToken() {
        return token;
    }

    public String getClientId() {
        return clientId;
    }

    public String getDatabaseURI() {
        return databaseURI;
    }
}
