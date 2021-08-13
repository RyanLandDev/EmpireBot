package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class ResourceStorageBuilding extends ResourceBuilding {

    public ResourceStorageBuilding(int stage, int health) {
        super(stage, health);
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.RESOURCE_STORAGE;
    }
}
