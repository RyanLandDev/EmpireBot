package net.ryanland.empire.sys.gameplay.collectible.perk;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.PerksSerializer;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
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
        profile.getDocument().setPotions(PerksSerializer.getInstance().serialize(perks));
        profile.getDocument().update();

        return new PresetBuilder(PresetType.SUCCESS,
            "Used " + format(), "Perk");
    }

    @Override
    public String format() {
        return Item.super.format() + " (" + DateUtil.formatRelative(getDuration()) + ")";
    }
}
