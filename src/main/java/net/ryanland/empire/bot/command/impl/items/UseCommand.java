package net.ryanland.empire.bot.command.impl.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.StringArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class UseCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("use")
                .description("Use an item from your /inventory.")
                .category(Category.ITEMS);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new StringArgument()
                        .id("item")
                        .description("The name of the item to use.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Item item = CollectibleHolder.findItem(event.getArgument("item"));
        if (!event.getProfile().getInventory().contains(item)) {
            throw new CommandException("You do not have this item in your inventory.");
        }

        event.reply(item.use(event.getProfile()));
    }
}
