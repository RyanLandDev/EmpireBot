package net.ryanland.empire.sys.gameplay.collectible.perk;

import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.util.DateUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
public abstract class Perk implements Item {

    private Date expires;

    public Perk(Date expires) {
        this.expires = expires;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public abstract Duration getDuration();

    public boolean has(Profile profile) {
        return profile.getPerks().contains(this);
    }

    @Override
    public String getHeadName() {
        return "Perk";
    }

    @Override
    public PresetBuilder use(Profile profile) {
        removeThisFromInventory(profile);

        List<Perk> perks = new ArrayList<>(profile.getPerks());
        setExpires(new Date(System.currentTimeMillis() + getDuration().toMillis()));
        perks.add(this);
        profile.setPerks(perks).update();

        return new PresetBuilder(DefaultPresetType.SUCCESS,
            "Used " + format(), "Perk");
    }

    @Override
    public String format() {
        return Item.super.format() + " (" + DateUtil.formatRelative(getDuration()) + ")";
    }
}
