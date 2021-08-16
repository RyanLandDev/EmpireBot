package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class ResourceGeneratorBuilding extends ResourceBuilding {

    protected Date lastCollect;

    @Override
    public Building deserialize(List<?> data) {
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
    public List<?> serialize() {
        return Arrays.asList(getId(), getStage(), getHealth(), (Object) getLastCollect());
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE_GENERATOR;
    }

    public Date getLastCollect() {
        return lastCollect;
    }

    public abstract Price<Double> getUnitPerMin();

}
