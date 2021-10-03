package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.executor.data.DisabledCommandHandler;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;
import net.ryanland.empire.sys.message.Emojis;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Command implements Emojis {

    public abstract CommandInfo getInfo();

    // Debugging -------------------------------

    protected final void debug(long message) {
        debug(String.valueOf(message));
    }

    protected final void debug(String message) {
        if (Empire.debugMode) {
            System.out.println(ANSI_YELLOW + "DEBUG" + ANSI_RESET + " - " +
                ANSI_BLUE + "/" + getName() + ANSI_RESET + " - " + message);
        }
    }

    // CommandData getters --------------------

    public final String getName() {
        return getInfo().getName();
    }

    public final String getUppercaseName() {
        return getName().substring(0, 1).toUpperCase() + getName().substring(1);
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

    public final boolean hasCooldown() {
        return getCooldown() != 0;
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

    public final List<SubCommand> getSubCommands() {
        return getInfo().getSubCommands();
    }

    public final boolean isDisabled() {
        return DisabledCommandHandler.getInstance().isDisabled(this);
    }

    public static Command of(String alias) {
        return CommandHandler.getCommand(alias);
    }

    // ---------------------------------------------------------

    public abstract ArgumentSet getArguments();

    public abstract void run(CommandEvent event) throws CommandException;
}
