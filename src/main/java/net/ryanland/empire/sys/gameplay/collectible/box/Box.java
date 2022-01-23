package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
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
        profile.update();
        return new PresetBuilder(DefaultPresetType.SUCCESS,
            "You got " + collectible.receive(profile),
            "Opened " + format()
        );
    }
}
