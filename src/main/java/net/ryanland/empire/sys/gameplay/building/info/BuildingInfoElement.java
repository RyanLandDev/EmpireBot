package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.message.Emojis;

public record BuildingInfoElement(String title, String emoji, String value, String description) implements Emojis {

    public BuildingInfoElement(String title, String emoji, int value, String description) {
        this(title, emoji, String.valueOf(value), description);
    }

    public String build() {
        return String.format(
                "%s **%s:** %s\n%s %s",
                emoji, title, value, AIR, description
        );
    }
}
