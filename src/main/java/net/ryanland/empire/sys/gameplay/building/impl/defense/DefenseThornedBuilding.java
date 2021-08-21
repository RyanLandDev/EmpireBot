package net.ryanland.empire.sys.gameplay.building.impl.defense;

import net.ryanland.empire.sys.gameplay.building.BuildingType;

public abstract class DefenseThornedBuilding extends DefenseBuilding {

    @Override
    public BuildingType getType() {
        return BuildingType.DEFENSE_THORNED;
    }
}
