package net.ryanland.empire.help;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.List;

public class HelpMaker {

    public static List<String> formattedUsageElements(CommandEvent event, Argument<?> highlighted) {
        Command command = event.getCommand();
        List<String> elements = new ArrayList<>();
        elements.add(event.getPrefix() + command.getName());

        for (Argument<?> argument : command.getArguments()) {
            String usage = argument.getName();
            if (argument.isOptional()) usage = "[" + usage + "]";
            else usage = "<" + usage + ">";

            // highlight check
            if (highlighted != null && highlighted.getId().equals(argument.getId())) {
                usage = "**" + usage + "**";
            }

            elements.add(usage);
        }

        return elements;
    }

    public static List<String> formattedUsageElements(CommandEvent event) {
        return formattedUsageElements(event, null);
    }

    public static String formattedUsage(CommandEvent event, Argument<?> highlighted) {
        return String.join(" ", formattedUsageElements(event, highlighted));
    }

    public static String formattedUsage(CommandEvent event) {
        return formattedUsage(event, null);
    }
}
