package net.ryanland.empire.bot.command.executor.functional_interface;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;

@FunctionalInterface
public interface CommandRunnable extends Runnable {

    @Override
    default void run() {
        throw new IllegalStateException("Use CommandRunnable#execute instead");
    }

    void execute() throws CommandException;
}
