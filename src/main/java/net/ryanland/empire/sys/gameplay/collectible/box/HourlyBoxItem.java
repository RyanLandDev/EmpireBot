package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

public class HourlyBoxItem extends Box {

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
        //TODO add 80% chance for one of all 1.5x 5min potions
    }

}
