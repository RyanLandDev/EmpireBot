package net.ryanland.empire.sys.gameplay.building.impl;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.currency.Price;

public abstract class Building {

    public abstract int getId();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getEmoji();

    public abstract Price getPrice();

    public abstract BuildingType getBuildingType();
}
