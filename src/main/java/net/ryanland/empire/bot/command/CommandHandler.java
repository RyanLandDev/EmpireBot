package net.ryanland.empire.bot.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.types.impl.CommandArgument;
import net.ryanland.empire.bot.command.executor.CommandExecutor;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandGroup;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHandler {

    private static final List<Command> COMMANDS = new ArrayList<>();
    private static final HashMap<String, Command> ALIASES = new HashMap<>();
    private static final CommandExecutor COMMAND_EXECUTOR = new CommandExecutor();

    public static void register(Command... commands) {
        for (Command command : commands) {
            if (command.getName() == null || command.getDescription() == null || command.getCategory() == null) {
                throw new IllegalStateException(command.getClass().getName() +
                        " - Commands must have at least a name, description and category.");
            }

            COMMANDS.add(command);
            ALIASES.put(command.getName(), command);

            for (String alias : command.getAliases()) {
                ALIASES.put(alias, command);
            }
        }
    }

    public static void upsertAll() throws InvalidTestGuildException {

        Guild testGuild = Empire.getJda().getGuildById("832384230331252816");
        if (testGuild == null) throw new InvalidTestGuildException("Bot is not on test guild or provided guild id is invalid!");

        for (Command command : COMMANDS) {
            net.ryanland.empire.bot.command.help.CommandData cmdData = command.getData();
            CommandData slashCmdData = new CommandData(cmdData.getName(), cmdData.getDescription());

            if (cmdData.getSubCommands() == null && cmdData.getSubCommandGroups() == null) {
                for (Argument<?> arg : command.getArguments()) {
                    slashCmdData.addOption(OptionType.STRING, arg.getName(), arg.getDescription(), !arg.isOptional());
                }
            } else if (cmdData.getSubCommandGroups() == null) {
                for (SubCommand subCmd : cmdData.getSubCommands()) {
                    SubcommandData subCmdData = new SubcommandData(subCmd.getName(), subCmd.getDescription());
                    for (Argument<?> arg : subCmd.getArguments()) {
                        subCmdData.addOption(OptionType.STRING, arg.getName(), arg.getDescription(), !arg.isOptional());
                    }
                    slashCmdData.addSubcommands(subCmdData);
                }
            } else {
                for(SubCommandGroup group : cmdData.getSubCommandGroups()) {
                    SubcommandGroupData subCmdGroupData = new SubcommandGroupData(group.getName(), group.getDescription());
                    for (SubCommand subCmd : group.getSubCommands()) {
                        SubcommandData subCmdData = new SubcommandData(subCmd.getName(), subCmd.getDescription());
                        for (Argument<?> arg : subCmd.getArguments()) {
                            subCmdData.addOption(OptionType.STRING, arg.getName(), arg.getDescription(), !arg.isOptional());
                        }
                        subCmdGroupData.addSubcommands(subCmdData);
                    }
                    slashCmdData.addSubcommandGroups(subCmdGroupData);
                }
            }


            if (Empire.useTestGuild) {
                testGuild.upsertCommand(slashCmdData).queue();
            } else {
                Empire.getJda().upsertCommand(slashCmdData).queue();
            }


        }
    }

    public static List<Command> getCommands() {
        return COMMANDS;
    }

    public static Command getCommand(String alias) {
        return ALIASES.get(alias);
    }

    public static void run(CommandEvent event) {
        COMMAND_EXECUTOR.run(event);
    }

    public static void execute(CommandEvent event, String[] args) {
        COMMAND_EXECUTOR.execute(event, args);
    }
}
