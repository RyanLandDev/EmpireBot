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

    public abstract int getRange();

    public abstract int getDamage();

    public abstract int getSpeed();

    public int getSpeedInMs() {
        return Math.max((int) Math.floor(getSpeed()), 100);
    }

    public double getSpeedInSec() {
        return Math.max((double) getSpeed() / 1000, 0.1d);
    }

}
