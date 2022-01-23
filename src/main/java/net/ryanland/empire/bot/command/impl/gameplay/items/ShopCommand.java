package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.arguments.Enum.ShopCategory;

@CommandBuilder(
    name = "shop",
    description = "The place to buy items."
)
public class ShopCommand extends ItemsCommand implements CombinedCommand {

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
    public void execute(CommandEvent event) throws CommandException {
        ShopCategory category = event.getArgument("category");

        event.reply(category.getTabMenuBuilder(event)
            .insertPage(0, SHOP + " Shop",
                new PresetBuilder("Use the buttons below to navigate through the shop.\n\u200b"),
                true)
        );
    }
}
