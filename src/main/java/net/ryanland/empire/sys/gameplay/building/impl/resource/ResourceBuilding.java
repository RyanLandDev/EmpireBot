package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public abstract class ResourceBuilding extends Building {

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE;
    }
}
