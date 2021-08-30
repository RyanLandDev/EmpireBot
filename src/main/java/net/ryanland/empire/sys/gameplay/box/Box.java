package net.ryanland.empire.sys.gameplay.box;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;

public abstract class Box implements Collectible {

    public abstract BoxItems getItems();

    @Override
    public String getHeadName() {
        return "Box";
    }

    @Override
    public String getEmoji() {
        return "📦";
    }

    @Override
    public boolean isStored() {
        return true;
    }
}
