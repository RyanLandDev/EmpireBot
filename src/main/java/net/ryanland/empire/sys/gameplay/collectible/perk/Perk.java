package net.ryanland.empire.sys.gameplay.collectible.perk;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.PotionsSerializer;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Perk implements Item {

    private final Date expires;

    public Perk(Date expires) {
        this.expires = expires;
    }

    public Date getExpires() {
        return expires;
    }

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

        List<Perk> userPotions = new ArrayList<>(profile.getPerks());
        setExpires(new Date(System.currentTimeMillis() + getLength().getDuration().toMillis()));
        userPotions.add(this);
        profile.getDocument().setPotions(PotionsSerializer.getInstance().serialize(userPotions));
        profile.getDocument().update();

        return new PresetBuilder(PresetType.SUCCESS,
            "Used " + format(), "Potion");
    }
}
