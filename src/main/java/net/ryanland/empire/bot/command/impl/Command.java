package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.disable.DisabledCommandHandler;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;

public abstract class Command {

    public abstract CommandData getData();

    // CommandData getters --------------------

    public final String getName() {
        return getData().getName();
    }

    public final String getUppercasedName() {
        return getName().substring(0, 1).toUpperCase() + getName().substring(1);
    }

    public final String[] getAliases() {
        return getData().getAliases();
    }

    public final String getDescription() {
        return getData().getDescription();
    }

    public final Category getCategory() {
        return getData().getCategory();
    }

    public final Permission getPermission() {
        return getData().getPermission();
    }

    public final int getCooldown() {
        return getData().getCooldown();
    }

    public final int getCooldownInMs() {
        return getCooldown() * 1000;
    }

    public final StorageType getCooldownStorageType() {
        return getData().getCooldownStorageType();
    }

    public final boolean requiresProfile() {
        return getData().isProfileRequired();
    }

    public final boolean isDisabled() {
        return DisabledCommandHandler.getInstance().isDisabled(this);
    }

    // ---------------------------------------------------------

    public abstract ArgumentSet getArguments();

    public abstract void run(CommandEvent event);
}
