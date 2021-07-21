package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public abstract class Command {

    public abstract String getName();

    public abstract String getDescription();

    public String[] getAliases() {
        return new String[0];
    }

    public boolean requiresProfile() {
        return false;
    }

    public abstract Permission getPermission();

    public abstract ArgumentSet getArguments();

    public abstract void run(CommandEvent event);

    public boolean userExecutable() {
        return true;
    }
}
