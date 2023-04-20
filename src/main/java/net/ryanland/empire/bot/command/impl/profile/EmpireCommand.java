package net.ryanland.empire.bot.command.impl.profile;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.RequiresProfile;
import net.ryanland.empire.bot.command.arguments.ProfileArgument;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;
import net.ryanland.empire.util.StringUtil;

@CommandBuilder(
    name = "empire",
    description = "View your or another user's empire."
)
public class EmpireCommand extends ProfileCommand implements CombinedCommand, RequiresProfile {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new ProfileArgument()
                .name("profile")
                .description("User to view Empire of")
                .optional(Profile::of)
        );
    }

    @Override
    public void execute(CommandEvent event) {
        Profile profile = event.getArgument("profile");
        User user = profile.getUser();

        InfoValueCollection infoValues = new InfoValueCollection()
            .addRegular("Level", profile.getLevel())
            .addCapacitable("Level XP", Emojis.XP, profile.getXp(), profile.getRequiredXp())
            .addRegular("Level progress", profile.getXpProgressBar() + "\n")
            .addRegular("Created", profile.getTimestampCreated())
            .addCapacitable("Gold", Emojis.GOLD,
                profile.getGold().formatAmount(), profile.getFormattedCapacity(Currency.GOLD, true))
            .addCapacitable("Crystals", Emojis.CRYSTALS,
                profile.getCrystals().formatAmount(), profile.getFormattedCapacity(Currency.CRYSTALS, true));

        String possessiveAdjective = event.getUser().equals(user) ? "your" : "their";
        PresetBuilder embed = new PresetBuilder(
            String.format("An overview of %s empire.\n\u200b", possessiveAdjective),
            String.format("%s Empire", StringUtil.capitalize(possessiveAdjective)))

            .setThumbnail(user.getAvatarUrl())
            .addField("__**Statistics**__",
                "\u200b\n" + infoValues.build() + "\n\u200b"
            )
            .addField("__**Empire Layout**__", "\u200b" +
                (event.getUser().equals(user) ? "\n:fast_forward: Get more info about a building with `/building <layer>`." : "") +
                "\n:information_source: To learn more about layers, type `/tutorial layers`.\n"
            )
            .addField(Emojis.HORIZONTAL_LINE, profile.getFormattedLayout());

        event.reply(embed);
    }
}
