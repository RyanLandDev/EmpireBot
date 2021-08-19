package net.ryanland.empire.bot.command.impl.info;

import groovyjarjarantlr.StringUtils;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.command.permissions.RankHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionButton;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenuBuilder;
import net.ryanland.empire.sys.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsCommand extends Command {

    private final String BOT_INVITE_LINK = "https://discord.com/oauth2/authorize?client_id=832988155355070555&permissions=8&scope=bot";
    private final String SERVER_INVITE_LINK = "https://discord.gg/D7SARkP7pA";
    private final String GITHUB_LINK = "https://github.com/RyanLandDev/EmpireBot";

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("stats")
                .description("Returns a bunch of stats about the bot.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        // Gets a hashmap of all the user IDs with ranks
        // DOES NOT WORK IF RankHandler'S HASHMAP IS OUT OF ORDER.
        HashMap<Long, Permission> userRanks = RankHandler.getUserRanks();
        // List to construct the final output
        List<String> devListBuilder = new ArrayList<>();

        Permission oldValue = null;
        // Iterates through hashmap keys and adds the developers to the list
        for (Long id : userRanks.keySet()) {
            Permission value = userRanks.get(id);
            if (value.equals(oldValue)) {
                devListBuilder.add(String.format("• <@%s>", id));
            } else {
                // Automatically creates a new category when a new permission level is reached.
                devListBuilder.add(String.format("\n**%s**",value.getName()));
                devListBuilder.add(String.format("• <@%s>", id));
                oldValue = value;
            }
        }

        InfoValueCollection infoValues = new InfoValueCollection()
                .addRegular("🏓 Ping","", String.format("`%s` ms.",
                        event.getJDA().getRestPing().complete()))
                .addRegular("🤝 Guild Count", "", String.format("`%s` Guilds",
                        event.getJDA().getGuilds().size()))
                .addRegular("💻 Current Shard", "",
                        "`N/A`")

                .addRegular(StringUtil.genTrimProofSpaces(50))

                .addRegular("✉ Bot Invite", "",
                        String.format("[Link](%s)", BOT_INVITE_LINK))
                .addRegular("ℹ Support Server", "",
                        String.format("[Link](%s)", SERVER_INVITE_LINK))
                .addRegular("👨‍💻 GitHub Repository", "",
                        String.format("[Link](%s)", GITHUB_LINK));

        ActionMenuBuilder actionMenuBuilder = new ActionMenuBuilder();

        event.reply(new ActionMenuBuilder()
                .setEmbed(new PresetBuilder()
                        .setTitle("Statistics")
                        .addLogo()
                        .setDescription("Here's some information about the bot.  \n\u200b")

                        .addField("__**Statistics**__",
                                "\u200b\n" + infoValues.build() + "\n\u200b",
                                true
                        ).addField("__**Credits**__\u200b\n",
                                String.join("\n", devListBuilder),
                                true
                        ))
                .addButtons(

                )
        );
    }
}
