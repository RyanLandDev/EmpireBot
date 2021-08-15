package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public class BuildingArgument extends SingleArgument<Building> {

    @Override
    public Building parsed(String argument, CommandEvent event) throws ArgumentException {
        try {
            return event.getUserDocument().getBuildings().get(Integer.parseInt(argument) - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException("No building exists at this position.");
        }
    }
}
