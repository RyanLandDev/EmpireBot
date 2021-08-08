package net.ryanland.empire.bot.command.executor;

public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }
}
