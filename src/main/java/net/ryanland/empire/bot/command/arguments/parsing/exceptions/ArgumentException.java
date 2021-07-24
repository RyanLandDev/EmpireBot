package net.ryanland.empire.bot.command.arguments.parsing.exceptions;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.bot.command.help.HelpMaker;

public class ArgumentException extends Exception {

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
