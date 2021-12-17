package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.SingleArgument;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public class BuildingArgument extends SingleArgument<Building> {

    @Override
    public OptionType getType() {
        return OptionType.INTEGER;
    }

    @Override
    public Building parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        try {
            return event.getProfile().getBuilding((int) argument.getAsLong());
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException("No building exists at this position.");
        }
    }
}
