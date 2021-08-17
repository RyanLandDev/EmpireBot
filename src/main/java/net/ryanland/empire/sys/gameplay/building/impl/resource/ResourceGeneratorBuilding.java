package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class ResourceGeneratorBuilding extends ResourceBuilding {

    protected Date lastCollect;

    @Override
    public Building deserialize(@NotNull List<?> data) {
        stage = (int) data.get(1);
        health = (int) data.get(2);
        lastCollect = (Date) data.get(3);
        return this;
    }

    @Override
    public Building defaults() {
        stage = BUILDING_START_STAGE;
        health = getMaxHealth();
        lastCollect = new Date();
        return this;
    }

    @Override
    public List<?> serialize(@NotNull Building building) {
        return Arrays.asList(building.getId(), building.getStage(), building.getHealth(),
                (Object) ((ResourceGeneratorBuilding) building).getLastCollect());
    }

    @Override
    public BuildingType getType() {
        return BuildingType.RESOURCE_GENERATOR;
    }

    @Override
    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return super.getBuildingInfoBuilder().replaceSegment(1, segment -> segment
                .insertElement(0, BuildingInfoElement.upgradable(
                        "Gold per minute", "🏭", getEffectiveCurrency().getEmoji(),
                        getUnitPerMin(), getUnitPerMin(stage + 1),
                        "The resources this building makes per minute."))
        );
    }

    @Override
    public Price<Integer> getHolding() {
        if (!isUsable()) {
            return new Price<>(getEffectiveCurrency(), 0);
        }

        double unitPerMs = getUnitPerMs().amount();
        long msDiff = new Date().getTime() - lastCollect.getTime();
        int holding = (int) Math.floor(unitPerMs * msDiff);

        return new Price<>(getEffectiveCurrency(), Math.min(holding, getCapacityInt()));
    }

    public Date getLastCollect() {
        return lastCollect;
    }

    public abstract Price<Integer> getUnitPerMin();

    public final Price<Integer> getUnitPerMin(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        Price<Integer> result = getUnitPerMin();
        this.stage = originalStage;

        return result;
    }

    public Price<Double> getUnitPerMs() {
        return new Price<>(getEffectiveCurrency(), (double) getUnitPerMin().amount() / 60000);
    }

    public final Price<Double> getUnitPerMs(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        Price<Double> result = getUnitPerMs();
        this.stage = originalStage;

        return result;
    }

}
