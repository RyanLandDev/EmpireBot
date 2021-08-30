package net.ryanland.empire.sys.gameplay.collectible.item;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;

public abstract class Item implements Collectible {

    @Override
    public String getHeadName() {
        return null;
    }

    @Override
    public boolean isStored() {
        return false;
    }
}
