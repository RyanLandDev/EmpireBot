package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class StringArgument extends SingleArgument<String> {

    @Override
    public String parsed(String argument, CommandEvent event) {
        return argument;
    }
}
