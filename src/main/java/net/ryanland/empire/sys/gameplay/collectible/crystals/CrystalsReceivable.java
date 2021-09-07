package net.ryanland.empire.sys.gameplay.collectible.crystals;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.collectible.Receivable;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.util.RandomUtil;

public abstract class CrystalsReceivable implements Receivable {

    @Override
    public String getHeadName() {
        return null;
    }

    @Override
    public String getEmoji() {
        return Emojis.CRYSTALS;
    }

    @Override
    public void receive(Profile profile) {
        profile.getDocument().setCrystals(profile.getCrystals().amount() + RandomUtil.randomInt(getMinimum(), getMaximum()));
        profile.getDocument().update();
    }

    public abstract int getMinimum();

    public abstract int getMaximum();

}
