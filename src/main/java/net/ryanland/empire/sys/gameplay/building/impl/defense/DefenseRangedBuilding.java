package net.ryanland.empire.sys.gameplay.building.impl.defense;

public abstract class DefenseRangedBuilding extends DefenseBuilding {

    public DefenseRangedBuilding(int stage, int health) {
        super(stage, health);
    }

    public abstract int getRange();

    public abstract int getDamage();

    protected abstract int getSpeed();

    public int getSpeedInMs() {
        return Math.max((int) Math.floor(getSpeed()), 100);
    }

    public double getSpeedInSec() {
        return Math.max((double) getSpeed() / 1000, 0.1d);
    }
}
