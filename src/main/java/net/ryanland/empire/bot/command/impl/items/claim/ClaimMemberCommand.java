package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.executor.cooldown.CooldownHandler;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.StorageType;
import net.ryanland.empire.util.DateUtil;

import java.time.OffsetDateTime;
import java.util.Date;

public class ClaimMemberCommand extends AbstractClaimSubCommand {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("member")
                .description("Claim your Member package.")
                .cooldown(86400)
                .cooldownStorage(StorageType.EXTERNAL)
                .requiresProfile();
    }

    @Override
    public ClaimCommandInfo getClaimInfo() {
        return ClaimCommandInfo.collectible(
                "Member", event -> {
                    if (!event.getGuild().getId().equals(Empire.SUPPORT_GUILD)) {
                        throw new CommandException("This reward can only be claimed in the **Empire server!**" +
                                "\nJoin here: " + Empire.SERVER_INVITE_LINK);
                    }

                    OffsetDateTime joined = event.getMember().getTimeJoined();
                    OffsetDateTime dayAgo = OffsetDateTime.now().minusDays(1);

                    if (!joined.isBefore(dayAgo)) {
                        throw new CommandException("You must be in this server for at least *24 hours* to claim this reward!" +
                                "\nTime left: " +
                                DateUtil.formatRelative(new Date((joined.toEpochSecond() - dayAgo.toEpochSecond()) * 1000)));
                    }

                    return true;
                }
        );
    }
}
