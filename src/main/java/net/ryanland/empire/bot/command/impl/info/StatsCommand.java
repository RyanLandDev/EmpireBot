package net.ryanland.empire.bot.command.impl.info;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.interactions.menu.ActionMenuBuilder;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CommandBuilder(
    name = "stats",
    description = "Returns a bunch of statistics about the bot."
)
public class StatsCommand extends InformationCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
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
                devListBuilder.add(String.format("\n**%s**", value.getName()));
                devListBuilder.add(String.format("• <@%s>", id));
                oldValue = value;
            }
        }

        InfoValueCollection infoValues = new InfoValueCollection()
            .addRegular("🏓 Ping", "", String.format("`%s` ms",
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
            .addButton(Button.link(Empire.SERVER_INVITE_LINK, "Support Server")
                .withEmoji(Emoji.fromMarkdown("⛑")))
            .addButton(Button.link(Empire.GITHUB_LINK, "GitHub Repository")
                .withEmoji(Emoji.fromMarkdown("👨‍💻"))
            ));
    }
}
