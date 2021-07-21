package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public abstract class SubCommandHolder extends Command {

    @Override
    public void run(CommandEvent event) {
        //TODO
    }

    public abstract SubCommand[] getSubCommands();
}
