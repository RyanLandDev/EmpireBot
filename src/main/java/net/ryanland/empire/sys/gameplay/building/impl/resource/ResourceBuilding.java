package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class ResourceBuilding extends Building {

    public abstract Price<Integer> getCapacity();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE;
    }

    public Currency getEffectiveCurrency() {
        return getCapacity().currency();
    }
}
