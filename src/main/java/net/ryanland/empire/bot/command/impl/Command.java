package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.executor.data.DisabledCommandHandler;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;

public abstract class Command {

    public abstract CommandInfo getInfo();

    // CommandData getters --------------------

    public final String getName() {
        return getInfo().getName();
    }

    public final String getUppercasedName() {
        return getName().substring(0, 1).toUpperCase() + getName().substring(1);
    }

    @Deprecated
    public final String[] getAliases() {
        return getInfo().getAliases();
    }

    public final String getDescription() {
        return getInfo().getDescription();
    }

    public final Category getCategory() {
        return getInfo().getCategory();
    }

    public final Permission getPermission() {
        return getInfo().getPermission();
    }

    public final int getCooldown() {
        return getInfo().getCooldown();
    }

    public final int getCooldownInMs() {
        return getCooldown() * 1000;
    }

    public final StorageType getCooldownStorageType() {
        return getInfo().getCooldownStorageType();
    }

    public final boolean requiresProfile() {
        return getInfo().isProfileRequired();
    }

    public final boolean isDisabled() {
        return DisabledCommandHandler.getInstance().isDisabled(this);
    }

    // ---------------------------------------------------------

    public abstract ArgumentSet getArguments();

    public abstract void run(CommandEvent event) throws CommandException;
}
