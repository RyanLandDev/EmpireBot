package net.ryanland.empire.sys.gameplay.collectible.box;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.collectible.Item;

public abstract class Box implements Item {

    public abstract BoxItems getItems();

    @Override
    public String getHeadName() {
        return "Box";
    }

    @Override
    public String getEmoji() {
        return "📦";
    }

    @Override
    public void use(Profile profile) {
        getItems().pick().receive(profile);
    }
}
