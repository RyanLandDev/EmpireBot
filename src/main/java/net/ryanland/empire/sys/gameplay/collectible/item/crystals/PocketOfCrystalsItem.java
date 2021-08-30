package net.ryanland.empire.sys.gameplay.collectible.item.crystals;

public class PocketOfCrystalsItem extends CrystalsItem {

    @Override
    public String getName() {
        return "Pocket of Crystals";
    }

    @Override
    public int getId() {
        return 10;
    }

    @Override
    public int getMinimum() {
        return 5;
    }

    @Override
    public int getMaximum() {
        return 20;
    }
}
