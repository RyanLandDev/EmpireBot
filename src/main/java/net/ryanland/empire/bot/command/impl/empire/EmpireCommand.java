package net.ryanland.empire.bot.command.impl.empire;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class EmpireCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("empire")
                .description("Returns information about your empire!")
                .category(Category.PROFILE)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);

        String[] statisticsString = new String[] {
                "__**Statistics**__",
                "\n**Level:** ", document.getLevel().toString(),
                "\n**XP:** ", document.getXp().toString(),
                "\n**Created:** ", String.valueOf(document.created),
                "\n**Gold:** ", document.getGold().toString(),
                "\n**Crystals:** ", document.getCrystals().toString()
        };


        event.reply(new PresetBuilder()
                .setTitle(String.format("%s's Empire",event.getUser().getName()))
                .setDescription("Here's all the information about your current Empire.")
                .addField("",
                        (String.join("",statisticsString)),
                        false)
        ).queue();
    }
}
