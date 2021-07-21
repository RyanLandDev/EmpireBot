package net.ryanland.empire.bot.command.arguments.types;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;

public abstract class SingleArgument<T> extends Argument<T> {

    @Override
    public T parse(Queue<String> arguments, CommandEvent event) throws ArgumentException {
        return parsed(arguments.remove(), event);
    }

    public abstract T parsed(String argument, CommandEvent event) throws ArgumentException;
}
