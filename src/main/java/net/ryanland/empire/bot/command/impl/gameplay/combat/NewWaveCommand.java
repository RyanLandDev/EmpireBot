package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;

public class NewWaveCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("newwave")
            .description("Starts a new enemy wave.")
            .category(Category.COMBAT)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        //TODO
    }
}
