package net.ryanland.empire.sys.gameplay.collectible.box.impl;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.box.Box;
import net.ryanland.empire.sys.gameplay.collectible.box.BoxItems;
import net.ryanland.empire.sys.gameplay.collectible.box.Boxes;

public class HourlyBoxItem extends Box {

    @Override
    public Boxes getEnum() {
        return Boxes.HOURLY;
    }

    @Override
    public BoxItems getItems() {
        return new BoxItems()
                .add(20, CollectibleHolder.get("Pocket of Crystals"));
        //TODO add 80% chance for one of all 1.5x 5min potions
    }

}
