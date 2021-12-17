package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.empire.bot.command.arguments.BuildingArgument;
import net.ryanland.empire.bot.command.arguments.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.List;

public class MoveCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("move")
            .description("Moves a building.")
            .category(Category.ITEMS)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet()
            .addArguments(
                new BuildingArgument()
                    .id("building")
                    .description("The position of the building to move."),
                new IntegerArgument()
                    .id("layer")
                    .description("Layer to move the building to.")
            );
    }

    @Override
    @SuppressWarnings("all")
    public void run(CommandEvent event) throws CommandException {
        // Get params
        Profile profile = event.getProfile();
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
        List<List> buildings = profile.getDocument().getBuildings();
        buildings.remove(building.getLayer() - 1);
        buildings.add(newLayer, building.serialize());
        profile.getDocument().setBuildingsRaw(buildings).update();

        // Send success message
        event.reply(new PresetBuilder(PresetType.SUCCESS, String.format(
            "Moved your %s from layer `%s` to `%s`.", building.format(), building.getLayer(), newLayer + 1
        )));
    }
}
