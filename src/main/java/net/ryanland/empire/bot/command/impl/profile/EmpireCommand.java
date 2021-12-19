package net.ryanland.empire.bot.command.impl.profile;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ProfileArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;

public class EmpireCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("empire")
            .description("View your or another user's empire.")
            .category(Category.PROFILE)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new ProfileArgument()
                .id("user")
                .description("User to view Empire of")
                .optional(CommandEvent::getProfile)
        );
    }

    @Override
    public void run(CommandEvent event) {
        Profile profile = event.getArgument("user");
        User user = profile.getUser();

        InfoValueCollection infoValues = new InfoValueCollection()
            .addRegular("Level", profile.getLevel())
            .addCapacitable("Level XP", XP, profile.getXp(), profile.getRequiredXp())
            .addRegular("Level progress", profile.getXpProgressBar() + "\n")
            .addRegular("Created", profile.getTimestampCreated())
            .addCapacitable("Gold", GOLD,
                profile.getGold().formatAmount(), profile.getFormattedCapacity(Currency.GOLD, true))
            .addCapacitable("Crystals", CRYSTALS,
                profile.getCrystals().formatAmount(), profile.getFormattedCapacity(Currency.CRYSTALS, true));

        PresetBuilder embed = new PresetBuilder(
            String.format("An overview of %s empire.\n\u200b", event.getPossessiveAdjective(user)),
            String.format("%s Empire", event.getCapitalizedPossessiveAdjective(user)))

            .setThumbnail(user.getAvatarUrl())
            .addField("__**Statistics**__",
                "\u200b\n" + infoValues.build() + "\n\u200b"
            )
            .addField("__**Empire Layout**__", "\u200b" +
                (event.isSelf(user) ? "\n:fast_forward: Get more info about a building with `/building <layer>`." : "") +
                "\n:information_source: To learn more about layers, type `/tutorial layers`.\n"
            )
            .addField(Emojis.HORIZONTAL_LINE, profile.getFormattedLayout());

        event.reply(embed);
    }
}
