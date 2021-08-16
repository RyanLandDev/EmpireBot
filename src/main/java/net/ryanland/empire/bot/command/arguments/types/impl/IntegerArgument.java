package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class IntegerArgument extends NumberArgument<Integer> {

    @Override
    public OptionType getType() {
        return OptionType.INTEGER;
    }

    @Override
    public Integer parsed(OptionMapping argument, CommandEvent event) {
        return Integer.parseInt(argument.getAsString());
    }
}
