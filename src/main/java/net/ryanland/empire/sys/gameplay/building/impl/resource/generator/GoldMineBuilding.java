package net.ryanland.empire.sys.gameplay.building.impl.resource.generator;

import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceGeneratorBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class GoldMineBuilding extends ResourceGeneratorBuilding {

    @Override
    public int getId() {
        return 11;
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
        return "⛏";
    }

    @Override
    public Price<Integer> getPrice() {
        return new Price<>(Currency.GOLD, 2250);
    }

    @Override
    public Price<Double> getUnitPerMin() {
        return new Price<>(Currency.GOLD, (stage - 1) * 2.75d + 24);
    }

    @Override
    public Price<Integer> getCapacity() {
        return new Price<>(Currency.GOLD, (stage - 1) * 90 + 425);
    }
}
