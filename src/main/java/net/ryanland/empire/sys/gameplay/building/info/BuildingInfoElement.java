package net.ryanland.empire.sys.gameplay.building.info;

public record BuildingInfoElement(String title, String emoji, String value, String description) {

    public BuildingInfoElement(String title, String emoji, int value, String description) {
        this(title, emoji, String.valueOf(value), description);
    }
}
