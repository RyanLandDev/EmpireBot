package net.ryanland.empire.sys.file.database.documents.impl;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.BaseDocument;
import net.ryanland.empire.sys.file.database.documents.SnowflakeDocument;
import net.ryanland.empire.sys.file.serializer.user.BuildingsSerializer;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDocument extends BaseDocument implements SnowflakeDocument {

    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_XP = 0;
    public static final int DEFAULT_GOLD = 1000;
    public static final int DEFAULT_CRYSTALS = 100;
    public static final int DEFAULT_WAVE = 1;
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_BUILDINGS = new ArrayList<>();

    public UserDocument(Document document) {
        super(document);
    }

    private int level = getLevel();
    private int crystals = getCrystals();
    private int gold = getGold();
    private int xp = getXp();
    private int wave = getWave();
    @SuppressWarnings("all")
    private List<List> buildings = getBuildingsRaw();
    private Date created;

    @Override
    public void updated(List<Bson> updates) {
        checkUpdate(updates, level, getLevel(), "lvl");
        checkUpdate(updates, crystals, getCrystals(), "crys");
        checkUpdate(updates, gold, getGold(), "gold");
        checkUpdate(updates, xp, getXp(), "xp");
        checkUpdate(updates, wave, getWave(), "wv");
        checkUpdate(updates, buildings, getBuildingsRaw(), "blds");
        checkUpdate(updates, created, getCreated(), "cr");

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
    public List<List> getBuildingsRaw() {
        return getList("blds", List.class, DEFAULT_BUILDINGS);
    }

    public List<Building> getBuildings() {
        return BuildingsSerializer.getInstance().deserialize(getBuildingsRaw());
    }

    public Date getCreated() {
        return getDate("cr");
    }

}
