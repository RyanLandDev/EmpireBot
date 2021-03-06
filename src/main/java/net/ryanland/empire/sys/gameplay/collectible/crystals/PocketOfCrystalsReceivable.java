package net.ryanland.empire.sys.gameplay.collectible.crystals;

public class PocketOfCrystalsReceivable extends CrystalsReceivable {

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
