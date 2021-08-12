package net.ryanland.empire.bot.command.impl.empire;

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
                .category(Category.EMPIRE);
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);
        document.clear();

        event.reply("Reset").setEphemeral(true).queue();
    }
}
