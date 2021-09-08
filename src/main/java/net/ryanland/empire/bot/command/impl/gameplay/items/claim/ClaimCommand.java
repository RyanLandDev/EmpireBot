package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;

public class ClaimCommand extends SubCommandHolder {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("claim")
            .description("Claim packages.")
            .category(Category.ITEMS)
            .subCommands(
                new ClaimHourlyCommand(),
                new ClaimDailyCommand(),
                new ClaimMemberCommand()
            )
            .requiresProfile();
    }
}
