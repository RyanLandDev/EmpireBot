package net.ryanland.empire.bot.command.impl.info.user;

import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.info.SubCommandGroup;

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
