package net.ryanland.empire.bot.command.impl.info;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.ryanland.colossus.command.BaseCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;

@CommandBuilder(
    name = "stats",
    description = "Returns a bunch of statistics about the bot."
)
public class StatsCommand extends BaseCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        InfoValueCollection infoValues = new InfoValueCollection()
            .addRegular("🏓 Ping", "", String.format("`%s` ms",
                event.getJDA().getRestPing().complete()))
            .addRegular("🤝 Guild Count", "", String.format("`%s` servers",
                event.getJDA().getGuilds().size()))
            .addRegular("💻 Current Shard",
                "`N/A`");

        event.reply(new PresetBuilder()
                .setTitle("Statistics")
                .addLogo()
                .setDescription("Here's some information about the bot.  \n\u200b")
                .addField("__**Statistics**__",
                    "\u200b\n" + infoValues.build() + "\n\u200b",
                    true
                ).addField("__**Credits**__\n\u200b\n\n",
                    """
                        **Owner**
                        • ryan_#2156
                        **Developer**
                        • Erobus#7861
                        """,
                    true
                ).addButtons(Button.link(Empire.BOT_INVITE_LINK, "Bot Invite").withEmoji(Emoji.fromUnicode("📧")),
                Button.link(Empire.SERVER_INVITE_LINK, "Support Server").withEmoji(Emoji.fromUnicode("⛑")),
                Button.link(Empire.GITHUB_LINK, "GitHub Repository").withEmoji(Emoji.fromUnicode("👨‍💻"))
            ));
    }
}
