package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;

public class EndlessStringArgument extends Argument<String> {

    @Override
    public String parse(Queue<String> arguments, CommandEvent event) {
        return String.join(" ", arguments);
    }
}
