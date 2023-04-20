package net.ryanland.empire.bot.command.impl.dev.balance;

import net.ryanland.colossus.command.BaseCommand;
import net.ryanland.colossus.command.BasicCommand;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.permission.PermissionHolder;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.command.regular.SubCommand;
import net.ryanland.colossus.command.regular.SubCommandHolder;
import net.ryanland.empire.bot.command.impl.dev.DeveloperCommand;

import java.util.List;

@CommandBuilder(
    name = "balance",
    description = "Modifies a user's balance values."
)
public class BalanceCommand extends DeveloperCommand implements SubCommandHolder {

    @Override
    public ArgumentSet getArguments() {
        return null;
    }

    @Override
    public List<SubCommand> registerSubCommands() {
        return List.of(new BalanceSetCommand(), new BalanceAddCommand(), new BalanceSubtractCommand());
    }
}
