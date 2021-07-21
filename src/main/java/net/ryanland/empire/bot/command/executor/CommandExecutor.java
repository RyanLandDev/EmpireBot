package net.ryanland.empire.bot.command.executor;

import net.dv8tion.jda.api.entities.Message;
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
        //TODO ... argument parsing, etc

        Message message = event.getMessage();
        String messageContent = message.getContentRaw();

        String[] args = messageContent.split("\\s+");
        String commandRequest = args[0].substring(event.getPrefix().length());

        Command command = CommandHandler.getCommand(commandRequest);
        event.setCommand(command);

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
