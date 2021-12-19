package net.ryanland.empire.bot.command.exceptions;

import net.ryanland.colossus.command.CommandException;

public class CannotAffordException extends CommandException {

    public CannotAffordException(String message) {
        super(message);
    }

}
