package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class IntegerArgument extends NumberArgument<Integer> {

    @Override
    public Integer parsed(String argument, CommandEvent event) {
        return Integer.parseInt(argument);
    }
}
