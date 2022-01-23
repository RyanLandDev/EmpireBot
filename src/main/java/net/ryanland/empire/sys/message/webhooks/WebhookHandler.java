package net.ryanland.empire.sys.message.webhooks;

import com.google.gson.JsonObject;
import net.ryanland.colossus.Colossus;

import java.io.IOException;

public class WebhookHandler {

    private static Webhook WEBHOOKS;

    static {
        try {
            JsonObject rawJson = Colossus.getLocalFile("webhooks").parseJson();
            WEBHOOKS = new Webhook(rawJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Webhook getWebhooks() {
        return WEBHOOKS;
    }

}
