package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.NumberArgument;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.events.MessageCommandEvent;
import net.ryanland.colossus.events.SlashEvent;
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
    public Building resolveSlashCommandArgument(SlashEvent event, OptionMapping arg) throws ArgumentException {
        return resolve((int) arg.getAsLong(), event);
    }

    @Override
    public Building resolveMessageCommandArgument(MessageCommandEvent event, String arg) throws ArgumentException {
        return resolve(Integer.parseInt(arg), event);
    }

    @Override
    public OptionType getSlashCommandOptionType() {
        return OptionType.INTEGER;
    }
}
