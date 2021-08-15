package net.ryanland.empire.bot.command.executor;

import net.ryanland.empire.bot.command.arguments.parsing.ArgumentParser;
import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.checks.CommandCheckException;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.executor.finalizers.CommandFinalizer;
import net.ryanland.empire.bot.command.executor.finalizers.CooldownFinalizer;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class CommandExecutor {

    public void run(CommandEvent event) {
        String[] args = event.getRawArgs();

        Command command = CommandHandler.getCommand(event.getName());
        if (command == null) return;
        event.setCommand(command);

        execute(event, args);
    }

    public void execute(CommandEvent event, String[] args) {
        Command command = event.getCommand();
        if (event.getSubCommandGroup() != null) {
            command = command.getInfo().getSubCommandGroupMap().get(event.getSubCommandGroup()).getSubCommand(event.getSubCommandName());
        } else if (event.getSubCommandName() != null) {
            command = command.getInfo().getSubCommandMap().get(event.getSubCommandName());
        }
        event.setCommand(command);

        try {
            for (CommandCheck check : CommandCheck.getChecks()) {
                if (check.check(event)) {
                    event.reply(check.buildMessage(event), true).queue();
                    throw new CommandCheckException();
                }
            }

            ArgumentParser argumentParser = new ArgumentParser(event, args);

            if (argumentParser.parseArguments()) {
                for (CommandFinalizer finalizer : CommandFinalizer.getFinalizers()) {
                    finalizer.finalize(event);
                }

                try {
                    command.run(event);
                } catch (Exception e) {
                    if (!(e instanceof CommandException)) e.printStackTrace();
                    event.reply(
                            new PresetBuilder(PresetType.ERROR,
                                    e instanceof CommandException ?
                                            e.getMessage() :
                                            "Unknown error, please report it to a developer."
                            )).queue();
                }
            }

        } catch (CommandCheckException ignored) {
        }
    }
}
