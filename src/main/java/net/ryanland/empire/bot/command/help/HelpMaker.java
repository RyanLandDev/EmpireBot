package net.ryanland.empire.bot.command.help;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.ArrayList;
import java.util.List;

public class HelpMaker {

    public static List<String> formattedUsageElements(CommandEvent event, Argument<?> highlighted) {
        Command command = event.getCommand();
        List<String> elements = new ArrayList<>();

        String[] rawArgs = event.getRawArgs();
        elements.add(event.getPrefix() + rawArgs[0]);

        if (command instanceof SubCommand) {
            elements.add(rawArgs[1]);
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
            if (argument.isOptional()) usage = String.format("[%s]", usage);
            else usage = String.format("<%s>", usage);

            // highlight check
            if (highlighted != null && highlighted.getId().equals(argument.getId())) {
                usage = String.format("**%s**", usage);
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

    public static String formattedUsageCode(CommandEvent event) {
        return "`" + formattedUsage(event) + "`";
    }

    public static String formattedAliases(String[] aliases) {
        return "`" + String.join("` `", aliases) + "`";
    }

    public static String formattedAliases(Command command) {
        return formattedAliases(command.getAliases());
    }

    public static String formattedAliases(CommandEvent event) {
        return formattedAliases(event.getCommand());
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

    public static PresetBuilder commandEmbed(CommandEvent event, Command command) {
        PresetBuilder embed = new PresetBuilder()
                .setTitle(command.getUppercasedName() + " Command")
                .setDescription(command.getDescription() + "\n\u200b")
                .addLogo()
                .addField("Category", command.getCategory().getName())
                .addField("Usage", String.format("```html\n%s\n```", formattedUsage(event)));

        if (command.getAliases().length > 0) {
            embed.addField("Aliases", formattedAliases(command));
        }

        if (command.getPermission() != Permission.USER) {
            embed.addField("Permission", command.getPermission().getName());
        }

        if (command.isDisabled()) {
            embed.setTitle(command.getUppercasedName() + " Command [Disabled]");
        }

        return embed;
    }

    public static String formattedQuickCommandList(List<Command> commands) {
        List<String> commandNames = new ArrayList<>();

        for (Command command : commands) {
            if (!command.isDisabled()) {
                commandNames.add(command.getName());
            }
        }

        return "`" + String.join("` `", commandNames) + "`";
    }
}
