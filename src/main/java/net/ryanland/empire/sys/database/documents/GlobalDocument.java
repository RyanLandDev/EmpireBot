package net.ryanland.empire.sys.database.documents;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.sys.database.MongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GlobalDocument extends BaseDocument {

    //TODO
    public GlobalDocument(Document document) {
        putAll(document);
    }

    // TODO disabled commands system ?
    private int level = getLevel();
    private int crystals = getCrystals();
    private int gold = getGold();
    private int xp = getXp();
    private int wave = getWave();

    public void update() {
        List<Bson> updates = new ArrayList<>();

        checkUpdate(updates, level, getLevel(), "level");
        checkUpdate(updates, crystals, getCrystals(), "crystals");
        checkUpdate(updates, gold, getGold(), "gold");
        checkUpdate(updates, xp, getXp(), "xp");
        checkUpdate(updates, wave, getWave(), "wave");

        MongoDB.USER_DB.updateMany(Filters.eq("id", getId()), updates);
    }

    public GlobalDocument setLevel(int level) {
        this.level = level;
        return this;
    }

    public GlobalDocument setCrystals(int crystals) {
        this.crystals = crystals;
        return this;
    }

    public GlobalDocument setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public GlobalDocument setXp(int xp) {
        this.xp = xp;
        return this;
    }

    public GlobalDocument setWave(int wave) {
        this.wave = wave;
        return this;
    }

    public String getId() {
        return getString("id");
    }

    public int getLevel() {
        return getInteger("level", 1);
    }

    public int getCrystals() {
        return getInteger("crystals", 0);
    }

    public int getGold() {
        return getInteger("gold", 0);
    }

    public int getXp() {
        return getInteger("xp", 0);
    }

    public int getWave() {
        return getInteger("wave", 1);
    }

    public Date getCreated() {
        return getDate("created");
    }

}
