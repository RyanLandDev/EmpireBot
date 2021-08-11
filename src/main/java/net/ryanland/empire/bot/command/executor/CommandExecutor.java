package net.ryanland.empire.bot.command.executor;

import net.ryanland.empire.bot.command.arguments.parsing.ArgumentParser;
import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.checks.CommandCheckException;
import net.ryanland.empire.bot.command.executor.checks.impl.CooldownCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.DisabledCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.PermissionCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.RequiresProfileCheck;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
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
            new RequiresProfileCheck(),
            new CooldownCheck()
    };

    private final CommandFinalizer[] finalizers = new CommandFinalizer[]{
            new CooldownFinalizer()
    };

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
            for (CommandCheck check : checks) {
                if (check.check(event)) {
                    event.reply(check.buildMessage(event), true).queue();
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
                    /*
                     * Remove the comment below to print the stack trace. Instead of the printstacktrace the bot sends
                     * the error message in chat in an embed, so if anything throws, the user notices. Advantage of that
                     * is that you don't have to use try catch blocks in your .run() methods, if something throws it ends up here :)
                     */
                    //if (!(e instanceof CommandException)) e.printStackTrace();
                    event.reply(
                            new PresetBuilder(PresetType.ERROR,
                                    e instanceof CommandException ?
                                            e.getMessage() :
                                            "An error occurred while trying to perform your command: ```\n" + e.getMessage() + "```"
                            ).setTitle("Oops, something went wrong..."), true).queue();
                }
            }

        } catch (CommandCheckException ignored) {
        }
    }
}
