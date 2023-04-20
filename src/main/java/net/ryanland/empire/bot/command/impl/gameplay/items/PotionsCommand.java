package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CommandBuilder(
    name = "potions",
    description = "List all currently active potions."
)
public class PotionsCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        List<Potion> potions = Profile.of(event).getPotions();

        event.reply(new PresetBuilder(
            "Here is a list of all potions you currently have active.\n\n" +
                (potions.size() == 0 ? "*None*" : potions.stream()
                    .map(potion -> MarkdownSanitizer.sanitize(potion.format()).replaceFirst("\\(\\d+min\\)$", "") +
                        " - " + DateUtil.formatRelative(new Date(potion.getExpires().getTime() - System.currentTimeMillis())))
                    .collect(Collectors.joining("\n"))),
            "Active Potions")
            .addLogo());
    }
}
