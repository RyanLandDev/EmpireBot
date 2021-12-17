package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.SingleArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;

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
            new SingleArgument<Item>() {
                @Override
                public Item parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
                    try {
                        return CollectibleHolder.findItem(argument.getAsString());
                    } catch (IllegalArgumentException e) {
                        throw new MalformedArgumentException("An item with the ID `" + argument.getAsString() + "` was not found.");
                    }
                }
            }
                .id("item")
                .description("The ID of the item to use.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Item item = event.getArgument("item");

        if (event.getProfile().getInventory().stream().noneMatch(invItem -> invItem.typeEquals(item))) {
            throw new CommandException("You do not have this item in your inventory.");
        }

        event.reply(item.use(event.getProfile()));
    }
}
