package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.NumberArgument;

public class IntegerArgument extends NumberArgument<Integer> {

    @Override
    public Integer parsed(String argument) {
        return Integer.parseInt(argument);
    }
}
