package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public abstract class SubCommand extends Command {

    @Override
    public String getName() {
        throw new IllegalStateException();
    }

    @Override
    public String getDescription() {
        throw new IllegalStateException();
    }

    @Override
    public boolean userExecutable() {
        return false;
    }
}
