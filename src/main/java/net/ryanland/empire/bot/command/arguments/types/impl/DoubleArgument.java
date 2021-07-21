package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class DoubleArgument extends NumberArgument<Double> {

    @Override
    public Double parsed(String argument, CommandEvent event) throws ArgumentException {
        return Double.parseDouble(argument);
    }
}
