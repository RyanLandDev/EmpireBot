package net.ryanland.empire.sys.gameplay.collectible.crystals;

public class PileOfCrystalsReceivable extends CrystalsReceivable {

    @Override
    public String getName() {
        return "Pile of Crystals";
    }

    @Override
    public int getId() {
        return 11;
    }

    @Override
    public int getMinimum() {
        return 20;
    }

    @Override
    public int getMaximum() {
        return 60;
    }

}
