package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class ResourceBuilding extends Building {

    public ResourceBuilding(int stage, int health) {
        super(stage, health);
    }

    public ResourceBuilding(int stage, int health, @Nullable Date lastCollect) {
        super(stage, health, lastCollect);
    }

    public abstract Price<Integer> getCapacity();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE;
    }
}
