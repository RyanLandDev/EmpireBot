package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.StorageType;

public class ClaimDailyCommand extends AbstractClaimSubCommand {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("daily")
                .description("Claim your Daily package.")
                .cooldown(86400)
                .cooldownStorage(StorageType.EXTERNAL)
                .requiresProfile();
    }

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return ClaimCommandInfo.collectible(
                "Daily", event -> true
        );
    }
}
