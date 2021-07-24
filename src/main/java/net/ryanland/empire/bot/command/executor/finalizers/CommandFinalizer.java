package net.ryanland.empire.bot.command.executor.finalizers;

import net.ryanland.empire.bot.events.CommandEvent;

public interface CommandFinalizer {

    // Finalizers are executed if a command is about to successfully run

    void finalize(CommandEvent event);
}
