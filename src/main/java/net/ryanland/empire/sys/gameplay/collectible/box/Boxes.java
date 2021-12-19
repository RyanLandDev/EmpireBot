package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

public enum Boxes {

    HOURLY("Hourly", 1),
    DAILY("Daily", 2),
    MEMBER("Member", 3),
    MYTHICAL("Mythical", 4);

    private final String name;
    private final int id;

    Boxes(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Box get() {
        return (Box) CollectibleHolder.getItem(name);
    }

    public String give(Profile profile) {
        return get().receive(profile);
    }
}
