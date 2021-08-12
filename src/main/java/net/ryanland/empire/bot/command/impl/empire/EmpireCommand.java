package net.ryanland.empire.bot.command.impl.empire;

import net.dv8tion.jda.api.EmbedBuilder;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Arrays;

public class EmpireCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("empire")
                .description("Returns information about your empire!")
                .category(Category.EMPIRE);
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);

        if (document == null) {
            event.reply(new PresetBuilder(PresetType.ERROR)
                    .setTitle("Error!")
                    .addField("You do not currently have an empire.","Make one with /start.",true)
                    .addLogo()
            ).setEphemeral(true).queue();
        } else {
            String[] statisticsString = new String[] {
                    "__**Statistics**__",
                    "\n**Level:** ", String.valueOf(document.getLevel()),
                    "\n**XP:** ", String.valueOf(document.getXp()),
                    "\n**Created:** ", String.valueOf(document.created),
                    "\n**Gold:** ", String.valueOf(document.getGold()),
                    "\n**Crystals:** ", String.valueOf(document.getCrystals())
            };


            event.reply(new EmbedBuilder()
                    .setTitle(String.format("%s's Empire",event.getUser().getName()))
                    .setDescription("Here's all the information about your current Empire.")
                    .addField("",
                            (String.join("",statisticsString)),
                            false)
            ).queue();
        }
    }
}
