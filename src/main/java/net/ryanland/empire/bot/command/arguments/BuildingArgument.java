package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.colossus.command.arguments.ArgumentOptionData;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.NumberArgument;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.events.command.MessageCommandEvent;
import net.ryanland.colossus.events.command.SlashCommandEvent;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public class BuildingArgument extends NumberArgument<Building> {

    private Building resolve(int pos, CommandEvent event) throws ArgumentException {
        try {
            return Profile.of(event).getBuilding(pos);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException("No building exists at this position.");
        }
    }

    @Override
    public ArgumentOptionData getArgumentOptionData() {
        return (ArgumentOptionData) new ArgumentOptionData(OptionType.INTEGER).setMinValue(1);
    }

    @Override
    public Building resolveSlashCommandArgument(SlashCommandEvent event, OptionMapping arg) throws ArgumentException {
        return resolve((int) arg.getAsLong(), event);
    }

    @Override
    public Building resolveMessageCommandArgument(MessageCommandEvent event, String arg) throws ArgumentException {
        return resolve(Integer.parseInt(arg), event);
    }


}
