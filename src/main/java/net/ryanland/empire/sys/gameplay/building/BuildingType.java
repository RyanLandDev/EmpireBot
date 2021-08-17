package net.ryanland.empire.sys.gameplay.building;

import net.ryanland.empire.sys.util.StringUtil;

public enum BuildingType {

    RESOURCE("Resource"),
    DEFENSE("Defense"),

    RESOURCE_GENERATOR("Generator", RESOURCE),
    RESOURCE_STORAGE("Storage", RESOURCE),

    DEFENSE_THORNED("Thorned", DEFENSE),
    DEFENSE_RANGED("Ranged", DEFENSE)
    ;

    private final String name;
    private final BuildingType baseType;

    BuildingType(String name) {
        this(name, null);
    }

    BuildingType(String name, BuildingType baseType) {
        this.name = name;
        this.baseType = baseType;
    }

    public BuildingType getBaseType() {
        return baseType;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return getBaseType().getName() + " / " + getName();
    }
}
