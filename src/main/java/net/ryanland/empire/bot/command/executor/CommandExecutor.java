package net.ryanland.empire.bot.command.executor;

import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.command.arguments.parsing.ArgumentParser;
import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.checks.CommandCheckException;
import net.ryanland.empire.bot.command.executor.checks.impl.CooldownCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.DisabledCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.PermissionCheck;
import net.ryanland.empire.bot.command.executor.finalizers.CommandFinalizer;
import net.ryanland.empire.bot.command.executor.finalizers.CooldownFinalizer;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class CommandExecutor {

    private final CommandCheck[] checks = new CommandCheck[]{
            new DisabledCheck(),
            new PermissionCheck(),
            new CooldownCheck()
    };

    private final CommandFinalizer[] finalizers = new CommandFinalizer[]{
            new CooldownFinalizer()
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
                for (CommandFinalizer finalizer : finalizers) {
                    finalizer.finalize(event);
                }

                try {
                    command.run(event);
                } catch (Exception e) {
                    event.reply(
                            new PresetBuilder(PresetType.ERROR,
                                    e.getMessage() == null ?
                                            "Unknown error, please report it to a developer." :
                                            e.getMessage()));
                }
            }

        } catch (CommandCheckException ignored) {
        }
    }
}
