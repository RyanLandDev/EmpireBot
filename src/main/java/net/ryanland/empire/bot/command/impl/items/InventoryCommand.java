package net.ryanland.empire.bot.command.impl.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class InventoryCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("inventory")
                .description("View all the items in your inventory.")
                .category(Category.ITEMS)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        PresetBuilder embed = new PresetBuilder(
                "Here is an overview of all the items in your inventory.\nYou can use an item with `/use <name>`\n\u200b",
                "Inventory");

        event.reply(embed);
    }
}
