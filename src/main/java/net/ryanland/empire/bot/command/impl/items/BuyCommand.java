package net.ryanland.empire.bot.command.impl.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.StringArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class BuyCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("buy")
                .description("Purchase an item from the /store.")
                .category(Category.ITEMS)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet()
                .addArguments(
                        new StringArgument()
                                .id("building")
                                .description("The name of the building to purchase.")
                );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Building building = Building.find(event.getArgument("building"));
        Profile profile = event.getProfile();

        building.getPrice().buy(profile);
        profile.addBuilding(building);
        profile.getDocument().update();

        event.reply(new PresetBuilder(PresetType.SUCCESS, String.format(
                "You purchased the %s for %s.\nView your information about your building using `/building %s`.",
                building.getFormattedName(), building.getPrice().format(), profile.getBuildings().size()
        )));
    }
}
