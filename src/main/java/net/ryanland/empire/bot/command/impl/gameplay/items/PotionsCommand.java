package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

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
                    .map(Potion::format)
                    .collect(Collectors.joining("\n"))),
            "Active Potions")
            .addLogo());
    }
}
