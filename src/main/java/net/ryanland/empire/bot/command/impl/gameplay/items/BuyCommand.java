package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentOptionData;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.NumberArgument;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.events.command.MessageCommandEvent;
import net.ryanland.colossus.events.command.SlashCommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

@CommandBuilder(
    name = "buy",
    description = "Purchase an item from the /store."
)
public class BuyCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new NumberArgument<Building>() {
                @Override
                public ArgumentOptionData getArgumentOptionData() {
                    return new ArgumentOptionData(OptionType.INTEGER);
                }

                @Override
                public Building resolveSlashCommandArgument(SlashCommandEvent event, OptionMapping arg) throws ArgumentException {
                    return resolve(((int) arg.getAsLong()));
                }

                @Override
                public Building resolveMessageCommandArgument(MessageCommandEvent event, String arg) throws ArgumentException {
                    return resolve(Integer.parseInt(arg));
                }

                public Building resolve(int pos) throws ArgumentException {
                    try {
                        return Building.of(pos);
                    } catch (IllegalArgumentException e) {
                        throw new MalformedArgumentException("A building with the ID `" + pos + "` was not found.");
                    }
                }
            }
                .name("building")
                .description("The ID of the building to purchase.")
            );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");
        Profile profile = Profile.of(event);

        building.getPrice().buy(profile);
        profile.addBuilding(building);
        profile.update();

        event.reply(new PresetBuilder(DefaultPresetType.SUCCESS, String.format(
            "You purchased the %s for %s.\nView your information about your building using `/building %s`.",
            building.format(), building.getPrice().format(), profile.getBuildings().size()
        )));
    }
}
