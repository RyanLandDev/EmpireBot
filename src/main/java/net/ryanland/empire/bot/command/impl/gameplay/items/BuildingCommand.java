package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.empire.bot.command.arguments.BuildingArgument;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

@CommandBuilder(
    name = "building",
    description = "Gets information about a specific building in your Empire."
)
public class BuildingCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new BuildingArgument()
                .name("building")
                .description("The position of the building to retrieve information from.")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");
        event.reply(building.getMenu());
    }

}
