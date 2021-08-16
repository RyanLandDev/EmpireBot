package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EndlessStringArgument extends Argument<String> {

    @Override
    public String parse(Queue<OptionMapping> arguments, CommandEvent event) {
        List<String> string = new ArrayList<>();
        arguments.forEach(arg -> string.add(arg.getAsString()));
        return String.join(" ", string);
    }
}
