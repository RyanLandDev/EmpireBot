package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class QuoteStringArgument extends Argument<String> {

    @Override
    public String parse(Queue<String> arguments, CommandEvent event) throws ArgumentException {
        final String ERROR_MESSAGE = "Invalid quote, must start and end with `'` or `\"`.";

        if (!(arguments.element().startsWith("'") || arguments.peek().startsWith("\""))) {
            throw new MalformedArgumentException(ERROR_MESSAGE);
        }

        List<String> elements = new ArrayList<>();
        String argument = arguments.poll();
        elements.add(argument);

        while (!(argument.endsWith("'") || argument.endsWith("\""))) {
            argument = arguments.poll();
            if (argument == null) throw new MalformedArgumentException(ERROR_MESSAGE);

            elements.add(argument);
        }

        // Join elements and remove quotes
        String result = String.join(" ", elements);
        return result.substring(1, result.length()-1);
    }
}
