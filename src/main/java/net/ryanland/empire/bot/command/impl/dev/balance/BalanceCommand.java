package net.ryanland.empire.bot.command.impl.dev.balance;

import net.ryanland.colossus.command.SubCommandHolder;

public class BalanceCommand extends SubCommandHolder {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("balance")
            .description("Modifies a user's balance values.")
            .category(Category.DEVELOPER)
            .permission(Permission.DEVELOPER)
            .subCommands(
                new BalanceSetCommand(),
                new BalanceAddCommand(),
                new BalanceSubtractCommand()
            );

    }
}
