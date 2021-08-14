package net.ryanland.empire.sys.gameplay.building.impl.defense.ranged;

import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseRangedBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public class ArcherBuilding extends DefenseRangedBuilding {

    public ArcherBuilding(int stage, int health) {
        super(stage, health);
    }

    @Override
    public int getId() {
        return 21;
    }

    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String getDescription() {
        return "Shoots arrows.";
    }

    @Override
    public String getEmoji() {
        return "🏹";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 750);
    }

    @Override
    public int getRange() {
        return (int) Math.floor(0.17 * (stage - 1) + 3);
    }

    @Override
    public int getDamage() {
        return (int) Math.floor(0.42 * (stage - 1) + 5);
    }

    @Override
    protected int getSpeed() {
        return -50 * (stage - 1) + 1000;
    }
}
