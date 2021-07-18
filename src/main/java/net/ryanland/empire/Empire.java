package net.ryanland.empire;

import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.ryanland.empire.bot.events.MessageEvent;
import net.ryanland.empire.sys.config.Config;
import net.ryanland.empire.sys.config.ConfigHandler;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Empire {

    private static JDA jda;
    private static Config config;

    public static void main(String[] args) throws IOException, LoginException {
        config = ConfigHandler.loadConfig();
        initialize(config);
    }

    private static void initialize(Config config) throws LoginException {


        JDABuilder builder = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("for " + config.getPrefix() + "help"))
                .setGatewayEncoding(GatewayEncoding.ETF)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .addEventListeners(new MessageEvent());

        jda = builder.build();
    }

    public static JDA getJda() {
        return jda;
    }

    public static Config getConfig() {
        return config;
    }
}
