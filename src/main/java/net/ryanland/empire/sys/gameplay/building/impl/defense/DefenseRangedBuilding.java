package net.ryanland.empire.sys.gameplay.building.impl.defense;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.action.BuffedAction;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.collectible.potion.DefenseBuildingDamagePotion;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
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
                .addElement(BuildingInfoElement.upgradable("Damage", DAMAGE,
                    new BuffedAction<String>() {
                        @Override
                        public Potion getPotion() {
                            return new DefenseBuildingDamagePotion();
                        }

                        @Override
                        public String run(Profile profile) {
                            if (multiplier == 1)
                                return String.valueOf(getDamage(stage));
                            else
                                return "***%s*** (x%s)"
                                    .formatted(String.valueOf(getDamage(stage) * multiplier).replaceAll(".0$", ""),
                                        multiplier);
                        }
                    }.perform(profile), String.valueOf(getDamage(stage + 1)),
                    "The amount of damage this building will deal per action."
                ))
                .addElement(BuildingInfoElement.upgradable("Speed", SPEED,
                    clean(NumberUtil.round(0.1, ((double) getSpeedInMs()) / 1000)) + "s",
                    clean(NumberUtil.round(0.1, ((double) getSpeedInMs(stage + 1)) / 1000)) + "s",
                "The action cooldown.")));
    }

    public abstract int getRange();

    public final int getRange(int stage) {
        return getProperty(this::getRange, stage);
    }

    public abstract int getDamage();

    public final int getDamage(int stage) {
        return getProperty(this::getDamage, stage);
    }

    protected abstract int getSpeed();

    public int getSpeedInMs() {
        return Math.max(getSpeed(), 100);
    }

    public final int getSpeedInMs(int stage) {
        return getProperty(this::getSpeedInMs, stage);
    }

    public double getSpeedInSec() {
        return Math.max((double) getSpeed() / 1000, 0.1d);
    }
}
