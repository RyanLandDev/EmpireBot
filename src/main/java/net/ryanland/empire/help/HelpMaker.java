package net.ryanland.empire.help;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.List;

public class HelpMaker {

    public static List<String> formattedUsageElements(CommandEvent event, Argument<?> highlighted) {
        Command command = event.getCommand();
        List<String> elements = new ArrayList<>();

        String[] rawArgs = event.getRawArgs();
        elements.add(event.getPrefix() + rawArgs[0]);

        if (command instanceof SubCommand) {
            elements.add(event.getRawArgs()[1]);
        }

        ArgumentSet arguments = command.getArguments();
        if (command instanceof SubCommandHolder) {
            try {
                SubCommand subcommand = event.getArgument("subcommand");
                elements.add(subcommand.getName());
                arguments.addAll(subcommand.getArguments());
            } catch (NullPointerException ignored) {
            }
        }

        for (Argument<?> argument : arguments) {
            String usage = argument.getName();
            if (argument.isOptional()) usage = "[" + usage + "]";
            else usage = "<" + usage + ">";

            // highlight check
            if (highlighted != null && highlighted.getId().equals(argument.getId())) {
                usage = "**" + usage + "**";
            }

            elements.add(usage);
        }

        if (command instanceof SubCommandHolder) {
            elements.add("...");
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

    public static String formattedSubCommands(SubCommand[] subcommands) {
        List<String> names = new ArrayList<>();

        for (SubCommand subcommand : subcommands) {
            names.add(subcommand.getName());
        }

        return "`" + String.join("`, `", names) + "`";
    }

    public static String formattedSubCommands(SubCommandHolder command) {
        return formattedSubCommands(command.getSubCommands());
    }

    public static String formattedSubCommands(Command command) {
        return formattedSubCommands((SubCommandHolder) command);
    }

    public static String formattedSubCommands(CommandEvent event) {
        return formattedSubCommands(event.getCommand());
    }

    public static String formattedSubCommandsUsage(SubCommand[] subcommands) {
        List<String> names = new ArrayList<>();

        for (SubCommand subcommand : subcommands) {
            names.add(subcommand.getName());
        }

        return String.join("/", names);
    }

    public static String formattedSubCommandsUsage(SubCommandHolder command) {
        return formattedSubCommandsUsage(command.getSubCommands());
    }

    public static String formattedSubCommandsUsage(Command command) {
        return formattedSubCommandsUsage((SubCommandHolder) command);
    }

    public static String formattedSubCommandsUsage(CommandEvent event) {
        return formattedSubCommandsUsage(event.getCommand());
    }
}
