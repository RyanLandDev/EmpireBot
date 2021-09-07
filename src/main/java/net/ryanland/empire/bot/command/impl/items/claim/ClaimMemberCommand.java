package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.StorageType;

public class ClaimMemberCommand extends AbstractClaimSubCommand {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("member")
                .description("Claim your Member package.")
                .cooldown(86400)
                .cooldownStorage(StorageType.EXTERNAL);
    }

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return ClaimCommandInfo.collectible(
                "Member", event -> false
        );//TODO
    }
}
