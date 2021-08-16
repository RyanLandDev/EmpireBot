package net.ryanland.empire.sys.gameplay.building.impl;

import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.defense.ranged.ArcherBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.thorned.WallBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceGeneratorBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.generator.GoldMineBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.storage.BankBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Building implements Serializable {

    public static final int BUILDING_START_STAGE = 1;
    public static final int BASE_MAX_HEALTH = 100;

    @SuppressWarnings("all")
    private static final Building[] BUILDINGS = new Building[]{

            // Defense Ranged
            new ArcherBuilding(),
            // Defense Thorned
            new WallBuilding(),

            // Resource Generator
            new GoldMineBuilding(),
            // Resource Storage
            new BankBuilding()

    };

    private static final HashMap<String, Building> NAME_BUILDINGS = new HashMap<>(
            Arrays.stream(BUILDINGS)
                    .collect(Collectors.toMap(Building::getName, Function.identity()))
    );

    private static final HashMap<Integer, Building> ID_BUILDINGS = new HashMap<>(
            Arrays.stream(BUILDINGS)
                    .collect(Collectors.toMap(Building::getId, Function.identity()))
    );

    protected int stage;
    protected int health;

    public Building deserialize(List<?> data) {
        stage = (int) data.get(1);
        health = (int) data.get(2);
        return this;
    }

    public Building deserialize() {
        stage = BUILDING_START_STAGE;
        health = getMaxHealth();
        return this;
    }

    public List<?> serialize() {
        return Arrays.asList(getId(), getStage(), getHealth());
    }

    public int getStage() {
        return stage;
    }

    public int getHealth() {
        return health;
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

    public static Building of(int id) {
        return ID_BUILDINGS.get(id);
    }

    public static Building of(String name) {
        return NAME_BUILDINGS.get(name);
    }

}
