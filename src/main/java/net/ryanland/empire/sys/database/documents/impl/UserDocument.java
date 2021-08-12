package net.ryanland.empire.sys.database.documents.impl;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.BaseDocument;
import net.ryanland.empire.sys.database.documents.SnowflakeDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Date;
import java.util.List;

public class UserDocument extends BaseDocument implements SnowflakeDocument {

    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_XP = 0;
    public static final int DEFAULT_GOLD = 1000;
    public static final int DEFAULT_CRYSTALS = 100;
    public static final int DEFAULT_WAVE = 1;
    public static final Date DEFAULT_DATE = new Date();

    public UserDocument(Document document) {
        super(document);
    }

    private int level = getLevel();
    private int crystals = getCrystals();
    private int gold = getGold();
    private int xp = getXp();
    private int wave = getWave();
    public Date created = getCreated();

    @Override
    public void updated(List<Bson> updates) {
        checkUpdate(updates, level, getLevel(), "level");
        checkUpdate(updates, crystals, getCrystals(), "crystals");
        checkUpdate(updates, gold, getGold(), "gold");
        checkUpdate(updates, xp, getXp(), "xp");
        checkUpdate(updates, wave, getWave(), "wave");
        checkUpdate(updates, created, getCreated(), "created");

        performUpdate(DocumentCache.USER_COLLECTION, Filters.eq("id", getId()), updates);
    }

    @Override
    public void cache() {
        DocumentCache.USER_CACHE.put(getId(), this);
    }

    // TODO buildings/layers

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

    public UserDocument setCreated(Date date) {
        this.created = date;
        return this;
    }

    @Override
    public String getId() {
        return getString("id");
    }

    public int getLevel() {
        return getInteger("level", DEFAULT_LEVEL);
    }

    public int getCrystals() {
        return getInteger("crystals", DEFAULT_CRYSTALS);
    }

    public int getGold() {
        return getInteger("gold", DEFAULT_GOLD);
    }

    public int getXp() {
        return getInteger("xp", DEFAULT_XP);
    }

    public int getWave() {
        return getInteger("wave", DEFAULT_WAVE);
    }

    public Date getCreated() {
        return this.created;
    }

}
