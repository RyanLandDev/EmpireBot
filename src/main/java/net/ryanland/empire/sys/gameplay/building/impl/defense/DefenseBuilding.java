package net.ryanland.empire.sys.gameplay.building.impl.defense;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public abstract class DefenseBuilding extends Building {

    @Override
    public BuildingType getType() {
        return BuildingType.DEFENSE;
    }

}
