package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.types.SingleArgument;

public class StringArgument extends SingleArgument<String> {

    @Override
    public String parsed(String argument) {
        return argument;
    }
}
