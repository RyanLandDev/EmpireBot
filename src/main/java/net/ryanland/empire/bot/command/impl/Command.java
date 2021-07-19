package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.permission.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public abstract class Command {

    public abstract String getName();

    public String[] getAliases() {
        return new String[0];
    }

    public abstract Permission getPermission();

    public abstract void run(CommandEvent event);

}
