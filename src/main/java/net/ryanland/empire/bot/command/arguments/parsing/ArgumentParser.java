package net.ryanland.empire.bot.command.arguments.parsing;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MissingArgumentException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ArgumentParser {

    private final CommandEvent event;
    private final String[] args;

    public ArgumentParser(CommandEvent event) {
        this.event = event;
        this.args = event.getMessage().getContentRaw().split("\\s+");
    }

    public ArgumentParser(CommandEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public boolean parseArguments() {
        Queue<String> queue = new LinkedList<>(Arrays.asList(args));
        queue.remove();
        ParsedArgumentMap parsedArgs = new ParsedArgumentMap();

        Command command = event.getCommand();
        PresetBuilder embed = new PresetBuilder(PresetType.ERROR);

        for (Argument<?> arg : command.getArguments()) {
            try {
                if (queue.peek() == null) throw new MissingArgumentException("Expected an argument, but got nothing.");
                Object parsedArg = arg.parse(queue, event);
                parsedArgs.put(arg.getId(), parsedArg);

            } catch (MalformedArgumentException e) {
                event.reply(embed
                        .setDescription(e.getMessage(event, arg))
                        .setTitle("Invalid Argument")
                );
                return false;

            } catch (MissingArgumentException e) {
                if (!arg.isOptional()) {
                    event.reply(embed
                            .setDescription(e.getMessage(event, arg))
                            .setTitle("Missing Argument")
                    );
                    return false;
                }

            } catch (ArgumentException ignored) {
            }
        }

        event.setParsedArgs(parsedArgs);
        return true;
    }
}