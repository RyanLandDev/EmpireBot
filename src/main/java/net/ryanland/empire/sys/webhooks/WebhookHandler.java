package net.ryanland.empire.sys.webhooks;

import com.google.gson.JsonObject;
import net.ryanland.empire.sys.externalfiles.ExternalFiles;

import java.io.IOException;

public class WebhookHandler {

    private static Webhook WEBHOOKS;

    public static void loadWebhooks() throws IOException {
        JsonObject rawJson = ExternalFiles.WEBHOOKS.parseJson();
        WEBHOOKS = new Webhook(rawJson);
    }

    public static Webhook getWebhooks() {
        return WEBHOOKS;
    }

}
