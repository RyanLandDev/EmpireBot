package net.ryanland.empire.sys.gameplay.collectible.crystals;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.Receivable;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
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
    public String receive(Profile profile) {
        int receivedCrystals = RandomUtil.randomInt(getMinimum(), getMaximum());

        profile.getDocument().setCrystals(profile.getCrystals().amount() + receivedCrystals);
        profile.getDocument().update();

        return new Price<>(Currency.CRYSTALS, receivedCrystals).format();
    }

    public abstract int getMinimum();

    public abstract int getMaximum();

}
