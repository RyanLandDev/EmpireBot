package net.ryanland.empire.bot.command.impl.info.user;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommandGroup;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.events.CommandEvent;

public class UserCommand extends SubCommandHolder {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("user")
                .description("Actions that deal with a user")
                .category(Category.INFORMATION)
                .subCommandGroups(
                        new SubCommandGroup("nickname", "Manages nickname actions",
                                new UserNickGetSubCommand(),
                                new UserNickSetSubCommand())
                );
    }

}
