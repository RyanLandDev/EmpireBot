package net.ryanland.empire.bot.command.impl.building;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.BuildingArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public class BuildingCommand extends Command {
//TODO
    @Override
    public CommandInfo getInfo() {
        return null;
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new BuildingArgument()
                    .id("building")
                    .description("The building to retrieve information from.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");
        event.reply(building.getName());
    }
}
