package net.ryanland.empire.bot.command.arguments.parsing.exceptions;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.executor.CommandException;
import net.ryanland.empire.bot.command.help.HelpMaker;
import net.ryanland.empire.bot.events.CommandEvent;

public class ArgumentException extends CommandException {

    private final String message;

    public ArgumentException(String message) {
        super(message);
        this.message = message;
    }

    public String getRawMessage() {
        return message;
    }

    public String getMessage(CommandEvent event, Argument<?> argument) {
        return HelpMaker.formattedUsage(event, argument)
                + "\n\n" + message;
    }
}
