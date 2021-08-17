package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;

public abstract class ResourceStorageBuilding extends ResourceBuilding {

    @Override
    public BuildingType getType() {
        return BuildingType.RESOURCE_STORAGE;
    }
}
