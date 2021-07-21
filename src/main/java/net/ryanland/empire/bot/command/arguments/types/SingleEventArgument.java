package net.ryanland.empire.bot.command.arguments.types;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

public abstract class SingleEventArgument<T> extends SingleArgument<T> {

    @Override
    public T parsed(String argument) throws ArgumentException {
        throw new IllegalStateException();
    }

    public abstract T parsed(String argument, CommandEvent event) throws ArgumentException;
}
