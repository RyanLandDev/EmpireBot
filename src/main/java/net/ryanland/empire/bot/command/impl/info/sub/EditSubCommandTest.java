package net.ryanland.empire.bot.command.impl.info.sub;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public class EditSubCommandTest extends SubCommand {

    @Override
    public Permission getPermission() {
        return Permission.DEVELOPER;
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(

        );
    }

    @Override
    public void run(CommandEvent event) {

    }
}
