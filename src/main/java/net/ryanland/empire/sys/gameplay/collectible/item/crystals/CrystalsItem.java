package net.ryanland.empire.sys.gameplay.collectible.item.crystals;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.collectible.item.Item;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.util.RandomUtil;

public abstract class CrystalsItem extends Item {

    @Override
    public String getEmoji() {
        return Emojis.CRYSTALS;
    }

    @Override
    public void give(Profile profile) {
        profile.getDocument().setCrystals(profile.getCrystals().amount() + RandomUtil.randomInt(getMinimum(), getMaximum()));
        profile.getDocument().update();
    }

    public abstract int getMinimum();

    public abstract int getMaximum();

}
