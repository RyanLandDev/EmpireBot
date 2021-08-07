package net.ryanland.empire;

import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.command.executor.disable.DisabledCommandHandler;
import net.ryanland.empire.bot.command.impl.dev.*;
import net.ryanland.empire.bot.command.impl.info.HelpCommand;
import net.ryanland.empire.bot.command.impl.info.PingCommand;
import net.ryanland.empire.bot.command.permissions.PermissionHandler;
import net.ryanland.empire.bot.command.permissions.RankHandler;
import net.ryanland.empire.bot.events.ButtonEvent;
import net.ryanland.empire.bot.events.MessageEvent;
import net.ryanland.empire.bot.events.logs.GuildTraffic;
import net.ryanland.empire.sys.config.Config;
import net.ryanland.empire.sys.config.ConfigHandler;
import net.ryanland.empire.sys.database.MongoDB;
import net.ryanland.empire.sys.database.documents.GlobalDocument;
import net.ryanland.empire.sys.webhooks.WebhookHandler;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class Empire {

    public static String RYANLAND = "RyanLand#0001";

    private static JDA jda;
    private static Config config;
    private static DisabledCommandHandler disabledCommandHandler;

    public static void main(String[] args) throws IOException, LoginException {

        config = ConfigHandler.loadConfig();
        initialize(config);
    }

    private static void initialize(Config config) throws IOException, LoginException {
        // Initialize
        MongoDB.initialize();
        PermissionHandler.loadPermissions();
        WebhookHandler.loadWebhooks();
        RankHandler.loadRanks();
        disabledCommandHandler = new DisabledCommandHandler();

        // Register commands
        CommandHandler.register(
                // Information
                new HelpCommand(),
                new PingCommand(),

                // Developer
                new MimicCommand(),
                new EvalCommand(),
                new PurgeCooldownsCommand(),
                new TestCommand(),
                new DisableCommand(),
                new EnableCommand(),
                new DevNamesCommand()
        );

        // Build bot
        JDABuilder builder = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("for " + config.getPrefix() + "help"))
                .setGatewayEncoding(GatewayEncoding.ETF)
                .setActivity(Activity.watching("empires | ;help"))
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .addEventListeners(
        // Register events
                    // General
                    new MessageEvent(),
                    new ButtonEvent(),
                    // Logs
                    new GuildTraffic()
                );

        // Build bot
        jda = builder.build();
    }

    public static JDA getJda() {
        return jda;
    }

    public static SelfUser getSelfUser() {
        return jda.getSelfUser();
    }

    public static String getLogo() {
        return getSelfUser().getAvatarUrl();
    }

    public static Config getConfig() {
        return config;
    }

    public static GlobalDocument getGlobalDocument() {
        return MongoDB.getGlobalDocument();
    }

    public static DisabledCommandHandler getDisabledCommandHandler() {
        return disabledCommandHandler;
    }
}
