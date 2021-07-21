package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;

public class DoubleArgument extends NumberArgument<Double> {

    @Override
    public Double parsed(String argument) throws ArgumentException {
        return Double.parseDouble(argument);
    }
}
