package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class LongArgument extends NumberArgument<Long> {

    @Override
    public Long parsed(String argument, CommandEvent event) {
        return Long.parseLong(argument);
    }
}
