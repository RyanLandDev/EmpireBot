package net.ryanland.empire.bot.command;

import net.ryanland.empire.bot.command.executor.CommandExecutor;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.HashMap;

public class CommandHandler {

    private static final HashMap<String, Command> COMMANDS = new HashMap<>();
    private static final HashMap<String, Command> ALIASES = new HashMap<>();
    private static final CommandExecutor COMMAND_EXECUTOR = new CommandExecutor();

    public static void register(Command... commands) {
        for (Command command : commands) {
            COMMANDS.put(command.getName(), command);
            ALIASES.put(command.getName(), command);

            for (String alias : command.getAliases()) {
                ALIASES.put(alias, command);
            }
        }
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
