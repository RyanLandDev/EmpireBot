package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.NumberArgument;

public class LongArgument extends NumberArgument<Long> {

    @Override
    public Long parsed(String argument) {
        return Long.parseLong(argument);
    }
}
