package net.ryanland.empire.sys.gameplay.currency;

import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.message.Emojis;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum Currency implements Emojis, EnumArgument.InputEnum {

    GOLD("Gold", Emojis.GOLD, 5000, 0.1f,
        Profile::getGold, Profile::setGold),
    CRYSTALS("Crystals", Emojis.CRYSTALS, 500, 2.3f,
        Profile::getCrystals, Profile::setCrystals);

    private final String name;
    private final String emoji;
    private final int defaultCapacity;
    private final float collectXpMultiplier;

    private final Function<Profile, Price<Integer>> getter;
    private final BiConsumer<Profile, Integer> setter;

    Currency(String name, String emoji, int defaultCapacity, float collectXpMultiplier,
             Function<Profile, Price<Integer>> getter, BiConsumer<Profile, Integer> setter) {
        this.name = name;
        this.emoji = emoji;
        this.defaultCapacity = defaultCapacity;
        this.collectXpMultiplier = collectXpMultiplier;

        this.getter = getter;
        this.setter = setter;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getDefaultCapacity() {
        return defaultCapacity;
    }

    public float getCollectXpMultiplier() {
        return collectXpMultiplier;
    }

    public Price<Integer> get(Profile profile) {
        return getGetter().apply(profile);
    }

    public Function<Profile, Price<Integer>> getGetter() {
        return getter;
    }

    /**
     * Updates a profile's currency value.
     * <strong>WARNING:</strong> Does not call {@link Profile#update}.
     *
     * @param profile The Profile to apply this change to.
     * @param newValue The new value this currency will be set to.
     */
    public void update(Profile profile, Integer newValue) {
        getSetter().accept(profile, newValue);
    }

    public BiConsumer<Profile, Integer> getSetter() {
        return setter;
    }

    public Price<Integer> getAmount(Profile profile) {
        return getGetter().apply(profile);
    }

    @Override
    public String getTitle() {
        return getName();
    }
}
