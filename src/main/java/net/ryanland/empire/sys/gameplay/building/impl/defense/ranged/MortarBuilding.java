package net.ryanland.empire.sys.gameplay.building.impl.defense.ranged;

import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseRangedBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public class MortarBuilding extends DefenseRangedBuilding {

    @Override
    public int getId() {
        return 23;
    }

    @Override
    public String getName() {
        return "Mortar";
    }

    @Override
    public String getDescription() {
        return "Made for abomination.";
    }

    @Override
    public String getEmoji() {
        return "💣";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 4500);
    }

    @Override
    public int getRange() {
        return (int) (1.1 * stage + 6);
    }

    @Override
    public int getDamage() {
        return (int) (1.3 * stage + 12);
    }

    @Override
    protected int getSpeed() {
        return -100 * stage + 5100;
    }
}
