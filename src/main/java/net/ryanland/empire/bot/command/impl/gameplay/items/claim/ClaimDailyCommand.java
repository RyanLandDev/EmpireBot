package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.colossus.command.regular.CommandBuilder;

@CommandBuilder(
    name = "daily",
    description = "Claim your Daily package.",
    cooldown = 24*60*60
)
public class ClaimDailyCommand extends AbstractClaimSubCommand {

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return ClaimCommandInfo.collectible(
            "Daily", event -> true
        );
    }
}
