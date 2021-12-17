package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.NumberArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
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
                new NumberArgument<Building>() {
                    @Override
                    public OptionType getType() {
                        return OptionType.INTEGER;
                    }

                    @Override
                    public Building parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
                        try {
                            return Building.of((int) argument.getAsLong());
                        } catch (IllegalArgumentException e) {
                            throw new MalformedArgumentException("A building with the ID `" + argument.getAsLong() + "` was not found.");
                        }
                    }
                }
                    .id("building")
                    .description("The ID of the building to purchase.")
            );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");
        Profile profile = event.getProfile();

        building.getPrice().buy(profile);
        profile.addBuilding(building);
        profile.getDocument().update();

        event.reply(new PresetBuilder(PresetType.SUCCESS, String.format(
            "You purchased the %s for %s.\nView your information about your building using `/building %s`.",
            building.format(), building.getPrice().format(), profile.getBuildings().size()
        )));
    }
}
