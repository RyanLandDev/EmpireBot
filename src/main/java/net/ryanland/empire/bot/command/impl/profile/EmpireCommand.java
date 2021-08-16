package net.ryanland.empire.bot.command.impl.profile;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.UserArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class EmpireCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("empire")
                .description("Gives information about an Empire.")
                .category(Category.PROFILE)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new UserArgument()
                    .id("user")
                    .description("User to view Empire of")
                    .optional(CommandEvent::getUser)
        );
    }

    @Override
    public void run(CommandEvent event) {
        User user = event.getArgument("user");

        event.reply(user.getName());

        /*
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);

        String[] statisticsString = new String[] {
                "__**Statistics**__",
                "\n**Level:** ", document.getLevel().toString(),
                "\n**XP:** ", document.getXp().toString(),
                "\n**Created:** ", String.valueOf(document.getCreated()),
                "\n**Gold:** ", document.getGold().toString(),
                "\n**Crystals:** ", document.getCrystals().toString()
        };


        event.performReply(new PresetBuilder()
                .setTitle(String.format("%s's Empire",event.getUser().getName()))
                .setDescription("Here's all the information about your current Empire.")
                .addField("",
                        (String.join("",statisticsString)),
                        false)
        ).queue();

         */
    }
}
