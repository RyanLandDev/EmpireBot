package net.ryanland.empire.sys.gameplay.building.impl.defense;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

public abstract class DefenseBuilding extends Building {

    public DefenseBuilding(int stage, int health) {
        super(stage, health);
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.DEFENSE;
    }

}
