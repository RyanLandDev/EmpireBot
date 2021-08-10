package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.CommandException;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommandGroup;
import net.ryanland.empire.bot.command.impl.info.UserSubCommands.NickGetCommand;
import net.ryanland.empire.bot.command.impl.info.UserSubCommands.NickSetCommand;
import net.ryanland.empire.bot.events.CommandEvent;

public class UserCommand extends Command {
    @Override
    public CommandData getData() {
        return new CommandData()
                .name("user")
                .description("Actions that deal with a user")
                .category(Category.INFORMATION)
                .subCommandGroups(
                        new SubCommandGroup("nickname", "Manages nickname actions",
                                new NickGetCommand(),
                                new NickSetCommand())
                );
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {

    }
}
