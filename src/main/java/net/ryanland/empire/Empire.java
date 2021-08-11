package net.ryanland.empire;

import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.executor.exceptions.InvalidSupportGuildException;
import net.ryanland.empire.sys.tutorials.TutorialHandler;
import net.ryanland.empire.bot.command.impl.dev.*;
import net.ryanland.empire.bot.command.impl.info.*;
import net.ryanland.empire.bot.command.permissions.PermissionHandler;
import net.ryanland.empire.bot.command.permissions.RankHandler;
import net.ryanland.empire.sys.tutorials.Tutorial;
import net.ryanland.empire.bot.events.ButtonEvent;
import net.ryanland.empire.bot.events.MessageEvent;
import net.ryanland.empire.bot.events.OnSlashCommandEvent;
import net.ryanland.empire.bot.events.logs.GuildTraffic;
import net.ryanland.empire.sys.file.config.Config;
import net.ryanland.empire.sys.file.config.ConfigHandler;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.MongoDB;
import net.ryanland.empire.sys.database.documents.impl.GlobalDocument;
import net.ryanland.empire.sys.message.webhooks.WebhookHandler;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class Empire {

    public static final String RYANLAND = "RyanLand#0001";
    public static final String SUPPORT_GUILD = "832384230331252816";

    public static final boolean useTestGuild = true;

    private static JDA jda;
    private static Config config;

    public static void main(String[] args) throws IOException, LoginException, InterruptedException, InvalidSupportGuildException {

        config = ConfigHandler.loadConfig();
        initialize(config);
    }

    private static void initialize(Config config) throws IOException, LoginException, InterruptedException, InvalidSupportGuildException {
        // Initialize
        MongoDB.initialize();
        PermissionHandler.loadPermissions();
        WebhookHandler.loadWebhooks();
        RankHandler.loadRanks();

        // Register commands
        CommandHandler.register(
                // Information
                new HelpCommand(),
                new PingCommand(),
                new UserCommand(),

                // Developer
                new MimicCommand(),
                new EvalCommand(),
                new PurgeCooldownsCommand(),
                new TestCommand(),
                new DisableCommand(),
                new EnableCommand(),
                new StopCommand(),
                new CreditsCommand(),
                new GuildInfoCommand(),
                new TutorialCommand()
        );

        // Register Tutorials
        TutorialHandler.register(
            Tutorial.CONTRIBUTE,
            Tutorial.BEST
        );

        // Build bot
        JDABuilder builder = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.ONLINE)
                .setGatewayEncoding(GatewayEncoding.ETF)
                .setActivity(Activity.watching("empires | /help"))
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .addEventListeners(
        // Register events
                    // General
                    new MessageEvent(),
                    new ButtonEvent(),
                    new OnSlashCommandEvent(),
                    // Logs
                    new GuildTraffic()
                );

        // Build bot
        jda = builder.build();
        jda.awaitReady();
        CommandHandler.upsertAll();
    }

    // Utility methods ------------------------------

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
        return DocumentCache.getGlobal();
    }
}
