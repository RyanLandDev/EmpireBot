package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.ArgumentStringResolver;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;

@CommandBuilder(
    name = "use",
    description = "Use an item from your /inventory."
)
public class UseCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new ArgumentStringResolver<Item>() {
                @Override
                public Item resolve(String arg, CommandEvent event) throws ArgumentException {
                    try {
                        return CollectibleHolder.findItem(arg);
                    } catch (IllegalArgumentException e) {
                        throw new MalformedArgumentException("An item with the ID `" + arg + "` was not found.");
                    }
                }
            }
                .name("item")
                .description("The ID of the item to use.")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Item item = event.getArgument("item");
        if (Profile.of(event).getInventory().stream().noneMatch(invItem -> invItem.typeEquals(item)))
            throw new CommandException("You do not have this item in your inventory.");
        event.reply(item.use(Profile.of(event)));
    }
}
