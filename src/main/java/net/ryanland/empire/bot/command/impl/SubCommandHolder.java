package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.SubCommandArgument;
import net.ryanland.empire.bot.command.help.HelpMaker;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.List;

public abstract class SubCommandHolder extends Command {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new SubCommandArgument()
                    .name(HelpMaker.formattedSubCommandsUsage(getSubCommands()))
                    .id("subcommand")
        );
    }

    @Override
    public void run(CommandEvent event) {
        SubCommand subcommand = event.getArgument("subcommand");
        event.setCommand(subcommand);

        List<String> args = event.getRawArgsAsList();
        args.remove(1);

        CommandHandler.execute(event, args.toArray(String[]::new));
    }

    public abstract SubCommand[] getSubCommands();
}
