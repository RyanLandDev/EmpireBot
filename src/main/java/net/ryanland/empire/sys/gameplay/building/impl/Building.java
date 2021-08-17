package net.ryanland.empire.sys.gameplay.building.impl;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.defense.ranged.ArcherBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.thorned.WallBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.generator.GoldMineBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.storage.BankBuilding;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfo;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionButton;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Building implements Serializable, Serializer<List<?>, Building>, Emojis {

    public static final int BUILDING_START_STAGE = 1;
    public static final int BASE_MAX_HEALTH = 100;
    public static final int USABLE_HEALTH = 75;
    public static final int LAYOUT_DISPLAY_PER_ROW = 7;

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

    protected int layer;
    protected Profile profile;

    @Override
    public Building deserialize(@NotNull List<?> data) {
        stage = (int) data.get(1);
        health = (int) data.get(2);
        return this;
    }

    public Building defaults() {
        stage = BUILDING_START_STAGE;
        health = getMaxHealth();
        return this;
    }

    public final List<?> serialize() {
        return serialize(this);
    }

    @Override
    public List<?> serialize(@NotNull Building building) {
        return Arrays.asList(building.getId(), building.getStage(), building.getHealth());
    }

    public final Building setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public final Building setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public int getHealth() {
        return health;
    }

    public int getLayer() {
        return layer;
    }

    public Profile getProfile() {
        return profile;
    }

    public int getMaxHealth() {
        return BASE_MAX_HEALTH;
    }

    public boolean isHealthMaxed() {
        return getHealth() >= getMaxHealth();
    }

    public boolean isUsable() {
        return health >= USABLE_HEALTH;
    }

    public final BuildingInfo getBuildingInfo() {
        return getBuildingInfoBuilder().build();
    }

    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return new BuildingInfoBuilder().setBuilding(this)
                .addSegment(new BuildingInfoSegmentBuilder()
                    .addElement("Layer", "🏘", getLayer(), String.format(
                            "Move this building to another layer using `/move %s <new layer>`.", getLayer()))
                    .addElement("Stage", "🧱", getStage(), String.format(
                            "Upgrade this building to __Stage %s__ for %s.", getStage() + 1, getUpgradePrice().format()))
                    .addElement("Health", "❤", getHealth(),
                            isHealthMaxed() ? "The building's health may go down when under attack." : String.format(
                                    "Repair this building for %s or %s.",
                                    getRepairPrice().format(true), getCrystalRepairPrice().format(true)
                            )))
                .addSegment(new BuildingInfoSegmentBuilder()
                    .addElement("Sell Price", "💵", getSellPrice().format(),
                            "Sell this building (irreversible).\n\u200b")
                );
    }

    public List<ActionButton> getActionButtons() {
        return new ArrayList<>();
        //TODO
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
                (int) Math.floor(0.15 * (stage + 1) * getPrice().amount()));
    }

    public Currency getMainCurrency() {
        return getPrice().currency();
    }

    public abstract int getId();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getEmoji();

    public abstract Price<Integer> getPrice();

    public abstract BuildingType getType();

    @SuppressWarnings("unchecked")
    public <R extends Building> R cast() {
        return (R) this;
    }

    public static Building of(int id) {
        return ID_BUILDINGS.get(id);
    }

    public static Building of(String name) {
        return NAME_BUILDINGS.get(name);
    }

}
