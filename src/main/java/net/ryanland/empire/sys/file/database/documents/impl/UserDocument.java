package net.ryanland.empire.sys.file.database.documents.impl;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.bot.command.executor.cooldown.Cooldown;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.BaseDocument;
import net.ryanland.empire.sys.file.database.documents.SnowflakeDocument;
import net.ryanland.empire.sys.file.serializer.BuildingsSerializer;
import net.ryanland.empire.sys.file.serializer.CooldownsSerializer;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.resource.generator.GoldMineBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.storage.BankBuilding;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserDocument extends BaseDocument implements SnowflakeDocument {

    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_XP = 0;
    public static final int DEFAULT_GOLD = 1000;
    public static final int DEFAULT_CRYSTALS = 100;
    public static final int DEFAULT_WAVE = 1;
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_BUILDINGS = Arrays.asList(
        Building.of(GoldMineBuilding.ID).defaults().serialize(),
        Building.of(BankBuilding.ID).defaults().serialize()
    );
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_COOLDOWNS = Collections.emptyList();
    public static final List<Integer> DEFAULT_INVENTORY = Collections.emptyList();

    public UserDocument(Document document) {
        super(document);
    }

    private int level = getLevel();
    private int crystals = getCrystals();
    private int gold = getGold();
    private int xp = getXp();
    private int wave = getWave();
    @SuppressWarnings("all")
    private List<List> buildings = getBuildings();
    private Date created = getCreated();
    @SuppressWarnings("all")
    private List<List> cooldowns = getCooldowns();
    private List<Integer> inventory = getInventory();

    @Override
    public void updated(List<Bson> updates) {
        checkUpdate(updates, level, getLevel(), "lvl");
        checkUpdate(updates, crystals, getCrystals(), "crys");
        checkUpdate(updates, gold, getGold(), "gold");
        checkUpdate(updates, xp, getXp(), "xp");
        checkUpdate(updates, wave, getWave(), "wv");
        checkUpdate(updates, buildings, getBuildings(), "blds");
        checkUpdate(updates, created, getCreated(), "cr");
        checkUpdate(updates, cooldowns, getCooldowns(), "cd");
        checkUpdate(updates, inventory, getInventory(), "inv");

        performUpdate(DocumentCache.USER_COLLECTION, Filters.eq("id", getId()), updates);
    }

    @Override
    public void cache() {
        DocumentCache.USER_CACHE.put(getId(), this);
    }

    public UserDocument setLevel(int level) {
        this.level = level;
        return this;
    }

    public UserDocument setCrystals(int crystals) {
        this.crystals = crystals;
        return this;
    }

    public UserDocument setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public UserDocument setXp(int xp) {
        this.xp = xp;
        return this;
    }

    public UserDocument setWave(int wave) {
        this.wave = wave;
        return this;
    }

    @SuppressWarnings("all")
    public UserDocument setBuildingsRaw(List<List> buildings) {
        this.buildings = buildings;
        return this;
    }

    public UserDocument setBuildings(List<Building> buildings) {
        return setBuildingsRaw(BuildingsSerializer.getInstance().serialize(buildings));
    }

    public UserDocument setCreated(@NotNull Date created) {
        this.created = created;
        return this;
    }

    @SuppressWarnings("all")
    public UserDocument setCooldownsRaw(List<List> cooldowns) {
        this.cooldowns = cooldowns;
        return this;
    }

    public UserDocument setCooldowns(List<Cooldown> cooldowns) {
        return setCooldownsRaw(CooldownsSerializer.getInstance().serialize(cooldowns));
    }

    public UserDocument setInventory(List<Integer> inventory) {
        this.inventory = inventory;
        return this;
    }

    @Override
    public String getId() {
        return getString("id");
    }

    public Integer getLevel() {
        return getInteger("lvl", DEFAULT_LEVEL);
    }

    public Integer getCrystals() {
        return getInteger("crys", DEFAULT_CRYSTALS);
    }

    public Integer getGold() {
        return getInteger("gold", DEFAULT_GOLD);
    }

    public Integer getXp() {
        return getInteger("xp", DEFAULT_XP);
    }

    public Integer getWave() {
        return getInteger("wv", DEFAULT_WAVE);
    }

    @SuppressWarnings("all")
    public List<List> getBuildings() {
        return getList("blds", List.class, DEFAULT_BUILDINGS);
    }

    public Date getCreated() {
        return getDate("cr");
    }

    @SuppressWarnings("all")
    public List<List> getCooldowns() {
        return getList("cd", List.class, DEFAULT_COOLDOWNS);
    }

    public List<Integer> getInventory() {
        return getList("inv", Integer.class, DEFAULT_INVENTORY);
    }

}
