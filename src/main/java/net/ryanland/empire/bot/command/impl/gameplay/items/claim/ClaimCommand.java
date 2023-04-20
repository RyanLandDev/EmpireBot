package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.command.regular.SubCommand;
import net.ryanland.colossus.command.regular.SubCommandHolder;
import net.ryanland.empire.bot.command.impl.gameplay.items.ItemsCommand;

import java.util.List;

@CommandBuilder(
    name = "claim",
    description = "Claim packages."
)
public class ClaimCommand extends ItemsCommand implements SubCommandHolder {

    @Override
    public ArgumentSet getArguments() {
        return null;
    }

    @Override
    public List<SubCommand> registerSubCommands() {
        return List.of(new ClaimHourlyCommand(), new ClaimDailyCommand(), new ClaimMemberCommand());
    }
}
