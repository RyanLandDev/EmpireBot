package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PotionsCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("potions")
            .description("List all currently active potions.")
            .category(Category.ITEMS)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        List<Potion> potions = event.getProfile().getPotions();

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
