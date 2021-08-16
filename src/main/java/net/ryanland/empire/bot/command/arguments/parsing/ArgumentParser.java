package net.ryanland.empire.bot.command.arguments.parsing;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArgumentParser {

    private final CommandEvent event;
    private final List<OptionMapping> args;

    public ArgumentParser(CommandEvent event) {
        this.event = event;
        this.args = event.getOptions();
    }

    public ArgumentParser(CommandEvent event, List<OptionMapping> args) {
        this.event = event;
        this.args = args;
    }

    public boolean parseArguments() {
        Queue<OptionMapping> queue = new LinkedList<>(args);
        ParsedArgumentMap parsedArgs = new ParsedArgumentMap();

        Command command = event.getCommand();
        PresetBuilder embed = new PresetBuilder(PresetType.ERROR);

        System.out.println("parsing");

        for (Argument<?> arg : command.getArguments()) {
            System.out.println("parsing argument "+arg.getId());
            try {
                Object parsedArg;
                if (queue.peek() == null && arg.isOptional()) {
                    parsedArg = arg.getOptionalFunction().apply(event);
                } else {
                    parsedArg = arg.parse(queue, event);
                }

                parsedArgs.put(arg.getId(), parsedArg);

            } catch (MalformedArgumentException e) {
                event.performReply(embed
                        .setDescription(e.getMessage(event, arg))
                        .setTitle("Invalid Argument")
                , true).queue();
                return false;

            } catch (ArgumentException ignored) {
            }
        }

        event.setParsedArgs(parsedArgs);
        return true;
    }
}