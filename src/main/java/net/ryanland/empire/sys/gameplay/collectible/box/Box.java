package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.Item;

public abstract class Box implements Item {

    public abstract BoxItems getItems();

    public abstract Boxes getEnum();

    @Override
    public final String getName() {
        return getEnum().getName();
    }

    @Override
    public final int getId() {
        return getEnum().getId();
    }

    @Override
    public String getHeadName() {
        return "Box";
    }

    @Override
    public String getEmoji() {
        return "📦";
    }

    @Override
    public PresetBuilder use(Profile profile) {
        removeThisFromInventory(profile);
        Collectible collectible = getItems().pick();
        profile.getDocument().update();
        return new PresetBuilder(PresetType.SUCCESS,
            "You got " + collectible.receive(profile),
            "Opened " + format()
        );
    }
}
