package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.interactions.menu.ConfirmMenu;
import net.ryanland.empire.bot.command.RequiresProfile;

@CommandBuilder(
    name = "reset",
    description = "Reset everything."
)
public class ResetCommand extends ProfileCommand implements CombinedCommand, RequiresProfile {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        event.reply(new ConfirmMenu(
            "**Are you sure?**\n\nThis will reset __EVERYTHING__ and __CANNOT__ be undone.",
            () -> Colossus.getDatabaseDriver().delete(event.getUser()),
            "Profile reset."
        ));
    }
}
