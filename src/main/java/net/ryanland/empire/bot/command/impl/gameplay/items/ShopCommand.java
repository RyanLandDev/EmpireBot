package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.empire.bot.command.arguments.Enum.ShopCategory;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;

public class ShopCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("shop")
            .description("The place to buy items.")
            .category(Category.ITEMS)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet()
            .addArguments(
                new EnumArgument<>(ShopCategory.class)
                    .id("category")
                    .description("The shop category to view.")
            );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        ShopCategory category = event.getArgument("category");

        event.reply(category.getTabMenuBuilder(event)
            .insertPage(0, SHOP + " Shop",
                new PresetBuilder("Use the buttons below to navigate through the shop.\n\u200b"),
                true)
        );
    }
}
