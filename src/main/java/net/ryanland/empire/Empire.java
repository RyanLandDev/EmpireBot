package net.ryanland.empire;

import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.ColossusBuilder;
import net.ryanland.empire.bot.command.impl.dev.*;
import net.ryanland.empire.bot.command.impl.dev.balance.BalanceCommand;
import net.ryanland.empire.bot.command.impl.gameplay.combat.EnemyCommand;
import net.ryanland.empire.bot.command.impl.gameplay.combat.NewWaveCommand;
import net.ryanland.empire.bot.command.impl.gameplay.combat.WaveCommand;
import net.ryanland.empire.bot.command.impl.gameplay.games.GambleCommand;
import net.ryanland.empire.bot.command.impl.gameplay.items.*;
import net.ryanland.empire.bot.command.impl.gameplay.items.claim.ClaimCommand;
import net.ryanland.empire.bot.command.impl.info.HelpCommand;
import net.ryanland.empire.bot.command.impl.info.PingCommand;
import net.ryanland.empire.bot.command.impl.info.StatsCommand;
import net.ryanland.empire.bot.command.impl.profile.CooldownsCommand;
import net.ryanland.empire.bot.command.impl.profile.EmpireCommand;
import net.ryanland.empire.bot.command.impl.profile.ResetCommand;
import net.ryanland.empire.bot.command.impl.profile.StartCommand;
import net.ryanland.empire.bot.command.inhibitor.RequiresProfileInhibitor;
import net.ryanland.empire.bot.events.logs.GuildTraffic;


public class Empire {

    public static final String SUPPORT_GUILD = "832384230331252816";

    // Settings
    public static final boolean debugMode = false;

    // Constant links
    public static final String BOT_INVITE_LINK = "https://discord.com/oauth2/authorize?client_id=832988155355070555&permissions=8&scope=bot";
    public static final String SERVER_INVITE_LINK = "https://discord.gg/D7SARkP7pA";
    public static final String GITHUB_LINK = "https://github.com/RyanLandDev/EmpireBot";

    public static void main(String[] args) {
        initialize();
    }

    private static void initialize() {
        Colossus colossus = new ColossusBuilder("src/config")
            .registerCommands(
                // Information
                new HelpCommand(),
                new PingCommand(),
                new StatsCommand(),

                // Developer
                new MimicCommand(),
                new EvalCommand(),
                new TestCommand(),
                new DisableCommand(),
                new EnableCommand(),
                new StopCommand(),
                new BalanceCommand(),

                // Profile
                new StartCommand(),
                new EmpireCommand(),
                new ResetCommand(),
                new CooldownsCommand(),

                // Items
                new BuildingCommand(),
                new MoveCommand(),
                new ShopCommand(),
                new BuyCommand(),
                new ClaimCommand(),
                new InventoryCommand(),
                new UseCommand(),
                new RepairCommand(),
                new PotionsCommand(),

                // Combat
                new NewWaveCommand(),
                new WaveCommand(),
                new EnemyCommand(),

                // Games
                new GambleCommand()
            )
            .registerInhibitors(
                new RequiresProfileInhibitor()
            )
            .disableHelpCommand()
            .setJDABuilder(builder -> builder
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setStatus(OnlineStatus.ONLINE)
                .setGatewayEncoding(GatewayEncoding.ETF)
                .setActivity(Activity.watching("empires | /help"))
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .addEventListeners(
                    // Register events
                    // Logs
                    new GuildTraffic()
                )).build();

        colossus.initialize();
    }
}
