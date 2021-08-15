package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
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
        DocumentCache.delete(event.getUserDocument().getId(), UserDocument.class);
        event.reply("Reset").setEphemeral(true).queue();
    }
}
