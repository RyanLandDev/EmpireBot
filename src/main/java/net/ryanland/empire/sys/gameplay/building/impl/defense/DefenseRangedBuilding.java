package net.ryanland.empire.sys.gameplay.building.impl.defense;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.util.NumberUtil;

import static net.ryanland.empire.util.NumberUtil.clean;

public abstract class DefenseRangedBuilding extends DefenseBuilding {

    @Override
    public BuildingType getType() {
        return BuildingType.DEFENSE_RANGED;
    }

    @Override
    @SuppressWarnings("all")
    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return super.getBuildingInfoBuilder()
            .insertSegment(1, new BuildingInfoSegmentBuilder()
                .addElement(BuildingInfoElement.upgradable("Range", ":rosette:",
                    getRange(), getRange(stage + 1),
                    "The amount of surrounding layers the building will defend."
                ))
                .addElement(BuildingInfoElement.upgradable("Damage", ":boom:",
                    getDamage(), getDamage(stage + 1),
                    "The amount of damage this building will deal per action."
                ))
                .addElement(BuildingInfoElement.upgradable("Speed", ":athletic_shoe:",
                    clean(NumberUtil.round(0.1, ((double) getSpeedInMs()) / 1000)) + "s",
                    clean(NumberUtil.round(0.1, ((double) getSpeedInMs(stage + 1)) / 1000)) + "s",
                "The action cooldown.")));
    }

    public abstract int getRange();

    public final int getRange(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        int result = getRange();
        this.stage = originalStage;

        return result;
    }

    public abstract int getDamage();

    public final int getDamage(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        int result = getDamage();
        this.stage = originalStage;

        return result;
    }

    protected abstract int getSpeed();

    public int getSpeedInMs() {
        return Math.max((int) Math.floor(getSpeed()), 100);
    }

    public final int getSpeedInMs(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        int result = getSpeedInMs();
        this.stage = originalStage;

        return result;
    }

    public double getSpeedInSec() {
        return Math.max((double) getSpeed() / 1000, 0.1d);
    }
}
