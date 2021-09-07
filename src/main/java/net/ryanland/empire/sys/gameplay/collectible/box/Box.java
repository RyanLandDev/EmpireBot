package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.ArrayList;
import java.util.List;

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
        List<Item> inventory = new ArrayList<>(profile.getInventory());
        inventory.remove(this);
        profile.getDocument().setInventory(InventorySerializer.getInstance().serialize(inventory));

        Collectible collectible = getItems().pick();

        return new PresetBuilder(PresetType.SUCCESS,
                "You got " + collectible.receive(profile),
                "Opened " + format()
        );
    }
}
