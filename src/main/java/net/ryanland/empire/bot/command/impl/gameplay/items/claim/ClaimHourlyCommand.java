package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.colossus.command.regular.CommandBuilder;

@CommandBuilder(
    name = "hourly",
    description = "Claim your Hourly package.",
    cooldown = 60*60
)
public class ClaimHourlyCommand extends AbstractClaimSubCommand {

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return ClaimCommandInfo.collectible(
            "Hourly", event -> true
        );
    }
}
