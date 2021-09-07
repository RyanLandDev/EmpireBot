package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

public class DailyBoxItem extends Box {

    @Override
    public String getName() {
        return "Daily";
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public BoxItems getItems() {
        return new BoxItems()
                .add(10, CollectibleHolder.get("Pile of Crystals"));
        //TODO add 30% chance for one of all 1.5x 5min potions
        //TODO add 40% chance for one of all 1.5x 20min potions
        //TODO add 20% chance for one of all 2x 5min potions
    }

}
