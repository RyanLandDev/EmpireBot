package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class FloatArgument extends NumberArgument<Float> {

    @Override
    public Float parsed(String argument, CommandEvent event) {
        return Float.parseFloat(argument);
    }
}
