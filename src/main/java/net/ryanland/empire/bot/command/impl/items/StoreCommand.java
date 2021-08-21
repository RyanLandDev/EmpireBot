package net.ryanland.empire.bot.command.impl.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.StoreCategory;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;

public class StoreCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("store")
                .description("The place to buy items.")
                .category(Category.ITEMS);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet()
                .addArguments(
                    new EnumArgument<>(StoreCategory.class)
                        .id("category")
                        .description("The store category to view.")
                );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        StoreCategory category = event.getArgument("category");

    }
}
