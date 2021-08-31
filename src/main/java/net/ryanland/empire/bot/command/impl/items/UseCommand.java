package net.ryanland.empire.bot.command.impl.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;

//TODO
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
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {

    }
}
