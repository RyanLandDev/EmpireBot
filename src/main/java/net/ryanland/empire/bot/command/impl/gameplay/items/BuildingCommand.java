package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.empire.bot.command.arguments.BuildingArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public class BuildingCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("building")
            .description("Gets information about a specific building in your Empire.")
            .category(Category.ITEMS)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new BuildingArgument()
                .id("building")
                .description("The position of the building to retrieve information from.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");
        event.reply(building.getMenuBuilder());
    }

}
