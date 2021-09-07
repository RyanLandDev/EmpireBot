package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

public class MemberBoxItem extends Box {

    @Override
    public String getName() {
        return "Member";
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public BoxItems getItems() {
        return ((Box) CollectibleHolder.getItem("Daily")).getItems();
    }

}
