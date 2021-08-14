package net.ryanland.empire.sys.gameplay.building.impl;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceGeneratorBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class Building {

    public static final int BASE_MAX_HEALTH = 100;

    protected final int stage;
    protected final int health;
    protected final Date lastCollect;

    public Building(int stage, int health) {
        this(stage, health, null);
    }

    public Building(int stage, int health, @Nullable Date lastCollect) {
        this.stage = stage;
        this.health = health;
        this.lastCollect = lastCollect;
    }

    public int getStage() {
        return stage;
    }

    public int getHealth() {
        return health;
    }

    public Date getLastCollect() {
        if (!(this instanceof ResourceGeneratorBuilding)) {
            throw new IllegalCallerException();
        }

        return lastCollect;
    }

    public int getMaxHealth() {
        return BASE_MAX_HEALTH;
    }

    public Price<Integer> getSellPrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(0.65f * stage * getPrice().amount()));
    }

    public Price<Integer> getRepairPrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(((double) getMaxHealth() - health) / 100 * getSellPrice().amount() * 0.10f));
    }

    public Price<Integer> getCrystalRepairPrice() {
        return new Price<>(Currency.CRYSTALS,
                (int) Math.max(Math.floor(getRepairPrice().amount() * 0.03), 1));
    }

    public Price<Integer> getUpgradePrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(0.15 * stage * getPrice().amount()));
    }

    public Currency getMainCurrency() {
        return getPrice().currency();
    }

    public abstract int getId();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getEmoji();

    public abstract Price<Integer> getPrice();

    public abstract BuildingType getBuildingType();

    @SuppressWarnings("unchecked")
    public <T extends Building> T cast() {
        return (T) this;
    }

}
