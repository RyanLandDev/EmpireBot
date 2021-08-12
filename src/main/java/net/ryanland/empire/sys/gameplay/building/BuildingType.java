package net.ryanland.empire.sys.gameplay.building;

public enum BuildingType {

    RESOURCE,
    DEFENSE,

    RESOURCE_GENERATOR(RESOURCE),
    RESOURCE_STORAGE(RESOURCE),
    ;

    private final BuildingType baseType;

    BuildingType() {
        this(null);
    }

    BuildingType(BuildingType baseType) {
        this.baseType = baseType;
    }

    public BuildingType getBaseType() {
        return baseType;
    }
}