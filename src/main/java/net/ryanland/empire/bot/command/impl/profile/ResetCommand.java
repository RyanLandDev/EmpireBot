package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.interactions.menu.confirm.ConfirmMenu;

public class ResetCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("reset")
                .description("Reset everything.")
                .category(Category.PROFILE)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        event.reply(new ConfirmMenu(
                "**Are you sure?**\n\nThis will reset __EVERYTHING__ and __CANNOT__ be undone.",
                () -> DocumentCache.delete(event.getUser().getId(), UserDocument.class),
                "Profile reset."
        ));
    }
}
