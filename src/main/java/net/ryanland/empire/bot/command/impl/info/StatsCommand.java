package net.ryanland.empire.bot.command.impl.info;

import groovyjarjarantlr.StringUtils;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.Empire;
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
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenuBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("stats")
                .description("Returns a bunch of statistics about the bot.")
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
                .addRegular("🏓 Ping","", String.format("`%s` ms",
                        event.getJDA().getRestPing().complete()))
                .addRegular("🤝 Guild Count", "", String.format("`%s` servers",
                        event.getJDA().getGuilds().size()))
                .addRegular("💻 Current Shard",
                        "`N/A`");

        event.reply(new ActionMenuBuilder()
                .setEmbed(new PresetBuilder()
                        .setTitle("Statistics")
                        .addLogo()
                        .setDescription("Here's some information about the bot.  \n\u200b")
                        .addField("__**Statistics**__",
                                "\u200b\n" + infoValues.build() + "\n\u200b",
                                true
                        ).addField("__**Credits**__\n\u200b\n\n",
                                String.join("\n", devListBuilder),
                                true
                        ))
                .addButton(Button.link(Empire.BOT_INVITE_LINK, "Bot Invite")
                        .withEmoji(Emoji.fromMarkdown("📧")))
                .addButton(Button.link(Empire.SERVER_INVITE_LINK,"Support Server")
                        .withEmoji(Emoji.fromMarkdown("⛑")))
                .addButton(Button.link(Empire.GITHUB_LINK, "GitHub Repository")
                        .withEmoji(Emoji.fromMarkdown("👨‍💻"))
                ));
    }
}
