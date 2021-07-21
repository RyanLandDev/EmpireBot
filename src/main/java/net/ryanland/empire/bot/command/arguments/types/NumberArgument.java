package net.ryanland.empire.bot.command.arguments.types;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;

public abstract class NumberArgument<T> extends SingleArgument<T> {

    @Override
    public T parse(Queue<String> arguments, CommandEvent event) throws ArgumentException {
        try {
            return parsed(arguments.remove());
        } catch (NumberFormatException e) {
            throw new MalformedArgumentException("Invalid number provided.");
        }
    }

    public abstract T parsed(String argument) throws ArgumentException;
}
