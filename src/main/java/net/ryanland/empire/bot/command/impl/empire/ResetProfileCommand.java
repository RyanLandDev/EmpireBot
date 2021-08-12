package net.ryanland.empire.bot.command.impl.empire;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;

public class ResetProfileCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("reset")
                .description("Resets your profile! This is IRREVERSIBLE.")
                .category(Category.PROFILE)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        UserDocument document = event.getUserDocument();
        DocumentCache.delete(document.getId(), UserDocument.class);

        event.reply("Reset").setEphemeral(true).queue();
    }
}
