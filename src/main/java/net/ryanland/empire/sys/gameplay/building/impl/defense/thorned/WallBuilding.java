package net.ryanland.empire.sys.gameplay.building.impl.defense.thorned;

import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseThornedBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public class WallBuilding extends DefenseThornedBuilding {

    public WallBuilding(int stage, int health) {
        super(stage, health);
    }

    @Override
    public int getId() {
        return 22;
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public String getDescription() {
        return "Hard to break.";
    }

    @Override
    public String getEmoji() {
        return "🧱";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 500);
    }

    @Override
    public int getMaxHealth() {
        return 70 * (stage - 1) + 500;
    }

}
