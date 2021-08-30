package net.ryanland.empire.sys.gameplay.box.impl;

import net.ryanland.empire.sys.gameplay.box.Box;
import net.ryanland.empire.sys.gameplay.box.BoxItems;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

public class HourlyBox extends Box {

    @Override
    public String getName() {
        return "Hourly";
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public BoxItems getItems() {
        return new BoxItems()
                .add(20, CollectibleHolder.get("Pocket of Crystals"));
    }

}
