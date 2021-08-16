package net.ryanland.empire.sys.gameplay.currency;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.message.Emojis;

import java.util.function.Function;
import java.util.function.Supplier;

public enum Currency implements Emojis {

    GOLD(Emojis.GOLD, 5000, Profile::getGold),
    CRYSTALS(Emojis.CRYSTALS, 500, Profile::getCrystals)
    ;

    private final String emoji;
    private final int defaultCapacity;
    private final Function<Profile, Price<Integer>> getter;

    Currency(String emoji, int defaultCapacity, Function<Profile, Price<Integer>> getter) {
        this.emoji = emoji;
        this.defaultCapacity = defaultCapacity;
        this.getter = getter;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getDefaultCapacity() {
        return defaultCapacity;
    }

    public Function<Profile, Price<Integer>> getGetter() {
        return getter;
    }

    public Price<Integer> getAmount(Profile profile) {
        return getGetter().apply(profile);
    }

    public Integer getAmountInt(Profile profile) {
        return getAmount(profile).amount();
    }
}
