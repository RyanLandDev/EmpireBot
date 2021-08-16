package net.ryanland.empire.bot.command.arguments.types.impl.number;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.types.NumberArgument;
import net.ryanland.empire.bot.events.CommandEvent;

public class LongArgument extends NumberArgument<Long> {

    @Override
    public Long parsed(OptionMapping argument, CommandEvent event) {
        return argument.getAsLong();
    }
}
