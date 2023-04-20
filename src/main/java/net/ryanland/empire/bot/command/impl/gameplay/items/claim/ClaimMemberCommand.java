package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.empire.Empire;
import net.ryanland.empire.util.DateUtil;

import java.time.OffsetDateTime;
import java.util.Date;

@CommandBuilder(
    name = "member",
    description = "Claim your Member package.",
    cooldown = 24*60*60
)
public class ClaimMemberCommand extends AbstractClaimSubCommand {

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
