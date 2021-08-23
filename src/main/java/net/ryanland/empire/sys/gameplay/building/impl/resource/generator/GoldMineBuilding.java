package net.ryanland.empire.sys.gameplay.building.impl.resource.generator;

import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceGeneratorBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

public class GoldMineBuilding extends ResourceGeneratorBuilding {

    public static final int ID = 11;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Gold Mine";
    }

    @Override
    public String getDescription() {
        return "Generates gold.";
    }

    @Override
    public String getEmoji() {
        return "<:goldmine:878439489398267965>";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 2250);
    }

    @Override
    public Price<Integer> getUnitPerMin() {
        return new Price<>(Currency.GOLD, (int) Math.floor((stage - 1) * 2.75d + 24));
    }

    @Override
    public Price<Integer> getCapacity() {
        return new Price<>(Currency.GOLD, (stage - 1) * 90 + 425);
    }

}
