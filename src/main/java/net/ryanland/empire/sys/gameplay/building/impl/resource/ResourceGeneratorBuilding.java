package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class ResourceGeneratorBuilding extends ResourceBuilding {

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE_GENERATOR;
    }

    public abstract Price<Double> getUnitPerMin();

}
