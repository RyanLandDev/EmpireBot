package net.ryanland.empire.sys.gameplay.building.impl.resource.storage;

import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceStorageBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public class BankBuilding extends ResourceStorageBuilding {

    public BankBuilding(int stage, int health) {
        super(stage, health);
    }

    @Override
    public int getId() {
        return 12;
    }

    @Override
    public String getName() {
        return "Bank";
    }

    @Override
    public String getDescription() {
        return "Stores gold.";
    }

    @Override
    public String getEmoji() {
        return "🏦";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 5000);
    }

    @Override
    public Price<Integer> getCapacity() {
        return new Price<>(Currency.GOLD, (stage - 1) * 1200 + 6000);
    }

}
