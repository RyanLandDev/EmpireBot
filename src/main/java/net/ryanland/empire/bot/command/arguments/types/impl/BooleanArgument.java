package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class BooleanArgument extends SingleArgument<Boolean> {

    @Override
    public Boolean parsed(String argument, CommandEvent event) throws ArgumentException {
        return Boolean.parseBoolean(argument);
    }
}
