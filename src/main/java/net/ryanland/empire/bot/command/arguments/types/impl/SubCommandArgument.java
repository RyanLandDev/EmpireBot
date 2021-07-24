package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.bot.command.help.HelpMaker;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SubCommandArgument extends SingleArgument<SubCommand> {

    @Override
    public SubCommand parsed(String argument, CommandEvent event) throws ArgumentException {
        try {
            return Arrays.stream(((SubCommandHolder) event.getCommand()).getSubCommands())
                    .filter(s -> s.getName().equals(argument)).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException(
                    "Not a valid subcommand. Choose from: " + HelpMaker.formattedSubCommands(event));
        }
    }
}
