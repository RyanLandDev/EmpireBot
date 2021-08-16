package net.ryanland.empire.bot.command.arguments.types.impl.number;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class DoubleArgument extends NumberArgument<Double> {

    @Override
    public Double parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        return Double.parseDouble(argument.getAsString());
    }
}
