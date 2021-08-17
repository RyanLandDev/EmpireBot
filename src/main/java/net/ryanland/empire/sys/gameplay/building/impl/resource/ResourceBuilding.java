package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public abstract class ResourceBuilding extends Building {

    public abstract Price<Integer> getCapacity();

    public Price<Integer> getCapacity(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        Price<Integer> result = getCapacity();
        this.stage = originalStage;

        return result;
    }

    public Integer getCapacityInt() {
        return getCapacity().amount();
    }

    public abstract Price<Integer> getHolding();

    @Override
    public BuildingType getType() {
        return BuildingType.RESOURCE;
    }

    public Currency getEffectiveCurrency() {
        return getCapacity().currency();
    }

    @Override
    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return super.getBuildingInfoBuilder()
                .insertSegment(1, new BuildingInfoSegmentBuilder()
                        .addElement("Holding", "👜", String.format(
                                        "%s / %s", getHolding().format(), getCapacity().formatAmount()),
                                "Collect holding resources.")
                        .addElement("Capacity", ":bucket:", String.format(
                                        "%s %s *%s*", getCapacity().format(), ARROW_RIGHT, getCapacity(stage + 1).formatAmount()),
                                "The maximum amount of resources this building can hold.")
                );
    }
}
