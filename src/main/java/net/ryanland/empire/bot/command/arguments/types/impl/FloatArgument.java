package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.NumberArgument;

public class FloatArgument extends NumberArgument<Float> {

    @Override
    public Float parsed(String argument) {
        return Float.parseFloat(argument);
    }
}
