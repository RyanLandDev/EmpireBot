package net.ryanland.empire.bot.command.executor;

import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.command.arguments.parsing.ArgumentParser;
import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.checks.CommandCheckException;
import net.ryanland.empire.bot.command.executor.checks.PermissionCheck;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

public class CommandExecutor {

    private final CommandCheck[] checks = new CommandCheck[]{
            new PermissionCheck()
    };

    public void run(CommandEvent event) {
        String[] args = event.getRawArgs();

        Command command = CommandHandler.getCommand(args[0]);
        if (command == null) return;
        event.setCommand(command);

        execute(event, args);
    }

    public void execute(CommandEvent event, String[] args) {
        Command command = event.getCommand();

        try {
            for (CommandCheck check : checks) {
                if (!check.check(event)) {
                    event.reply(check.buildMessage(event));
                    throw new CommandCheckException();
                }
            }

            ArgumentParser argumentParser = new ArgumentParser(event, args);

            if (argumentParser.parseArguments()) {
                command.run(event);
            }

        } catch (CommandCheckException ignored) {
        }
    }
}
