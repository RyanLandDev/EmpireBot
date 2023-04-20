package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.primitive.IntegerArgument;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.arguments.BuildingArgument;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

import java.util.List;

@CommandBuilder(
    name = "move",
    description = "Moves a building."
)
public class MoveCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new BuildingArgument()
                .name("building")
                .description("The position of the building to move."),
            new IntegerArgument()
                .name("layer")
                .description("Layer to move the building to.")
        );
    }

    @Override
    @SuppressWarnings("all")
    public void execute(CommandEvent event) throws CommandException {
        // Get params
        Profile profile = Profile.of(event);
        Building building = event.getArgument("building");
        Integer newLayer = (Integer) event.getArgument("layer") - 1;

        // Check if the building is at max health
        if (!building.isHealthMaxed())
            throw new CommandException(
                "Buildings can only be moved at max health. Repair your building using `/building` or `/repair`.");

        // Check if the position is valid
        if (newLayer < 0 || newLayer >= profile.getBuildings().size())
            throw new CommandException(String.format("`%s` is an invalid position.", newLayer + 1));
        if (building.getLayer() == newLayer)
            throw new CommandException("The building was already at this position.");

        // Update the building data
        List<Building> buildings = profile.getBuildings();
        buildings.remove(building.getLayer() - 1);
        buildings.add(newLayer, building);
        profile.setBuildings(buildings);
        profile.update();

        // Send success message
        event.reply(new PresetBuilder(DefaultPresetType.SUCCESS, String.format(
            "Moved your %s from layer `%s` to `%s`.", building.format(), building.getLayer(), newLayer + 1
        )));
    }
}
