package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.StorageType;

public class ClaimHourlyCommand extends AbstractClaimSubCommand {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("hourly")
                .description("Claim your Hourly package.")
                .cooldown(3600)
                .cooldownStorage(StorageType.EXTERNAL);
    }

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return new ClaimCommandInfo(
            "Hourly", "Received <todo> box",//TODO
                event -> true,
                event -> {
                    //TODO give box
                }
        );
    }
}
