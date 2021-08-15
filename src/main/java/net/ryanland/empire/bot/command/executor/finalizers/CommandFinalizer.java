package net.ryanland.empire.bot.command.executor.finalizers;

import net.ryanland.empire.bot.events.CommandEvent;

/**
 * Command finalizers are executed if a command is about to successfully run
  */
public abstract class CommandFinalizer {

    @SuppressWarnings("all")
    private final static CommandFinalizer[] FINALIZERS = new CommandFinalizer[]{
            new CooldownFinalizer()
    };

    public static CommandFinalizer[] getFinalizers() {
        return FINALIZERS;
    }

    public abstract void finalize(CommandEvent event);
}
