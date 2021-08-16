package net.ryanland.empire.bot.command.impl.dev.balance;

import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.info.SubCommandGroup;

public class BalanceCommand extends SubCommandHolder {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("balance")
                .description("Modifies a user's balance values.")
                .category(Category.DEVELOPER)
                .subCommandGroups(
                        new SubCommandGroup("actions", "Manages balance actions.",
                                new BalanceSetCommand(),
                                new BalanceAddCommand(),
                                new BalanceSubtractCommand())
                        );

    }
}
