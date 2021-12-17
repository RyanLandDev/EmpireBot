package net.ryanland.empire.sys.gameplay.currency;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.Emojis;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum Currency implements Emojis, EnumArgument.InputEnum {

    GOLD("Gold", Emojis.GOLD, 5000, 0.1f,
        Profile::getGold, UserDocument::setGold),
    CRYSTALS("Crystals", Emojis.CRYSTALS, 500, 2.3f,
        Profile::getCrystals, UserDocument::setCrystals);

    private final String name;
    private final String emoji;
    private final int defaultCapacity;
    private final float collectXpMultiplier;

    private final Function<Profile, Price<Integer>> getter;
    private final BiConsumer<UserDocument, Integer> setter;

    Currency(String name, String emoji, int defaultCapacity, float collectXpMultiplier,
             Function<Profile, Price<Integer>> getter, BiConsumer<UserDocument, Integer> setter) {
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

    public void update(@NotNull Profile profile, Integer newValue) {
        update(profile.getDocument(), newValue);
    }

    /**
     * Updates a UserDocument's currency value.
     * <strong>WARNING:</strong> Does not call {@link UserDocument#update}.
     *
     * @param document The UserDocument to apply this change to.
     * @param newValue The new value this currency will be set to.
     */
    public void update(UserDocument document, Integer newValue) {
        getSetter().accept(document, newValue);
    }

    public BiConsumer<UserDocument, Integer> getSetter() {
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
