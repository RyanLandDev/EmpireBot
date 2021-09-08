package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
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
                .addElement(BuildingInfoElement.capacitable("Holding", "👜",
                    getHolding().currency(), getHolding(), getCapacity(),
                    "Collect holding resources.", true))
                .addElement(BuildingInfoElement.upgradable("Capacity", ":bucket:",
                    getEffectiveCurrency(), getCapacity(), getCapacity(stage + 1),
                    "The maximum amount of resources this building can hold."))
            );
    }
}
