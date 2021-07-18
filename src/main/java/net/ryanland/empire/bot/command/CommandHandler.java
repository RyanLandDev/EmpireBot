package net.ryanland.empire.bot.command;

import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.HashMap;

public class CommandHandler {

    private static HashMap<String, Command> COMMANDS = new HashMap<>();
    private static HashMap<String, Command> ALIASES = new HashMap<>();

    public static void register(Command... commands) {
        for (Command command : commands) {
            COMMANDS.put(command.getName(), command);
            ALIASES.put(command.getName(), command);

            for (String alias : command.getAliases()) {
                ALIASES.put(alias, command);
            }
        }
    }

    public Command getCommand(String alias) {
        return ALIASES.get(alias);
    }

    public static void run(CommandEvent event) {
        //TODO ...
    }

}
