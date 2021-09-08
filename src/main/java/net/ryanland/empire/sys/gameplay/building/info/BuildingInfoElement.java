package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.InfoValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record BuildingInfoElement(String title, String emoji, String value, String description) implements Emojis {

    public BuildingInfoElement(String title, String emoji, int value, String description) {
        this(title, emoji, String.valueOf(value), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          String valueEmoji, Number currentValue, Number nextValue,
                                                          String description) {
        return new BuildingInfoElement(title, emoji,
            new InfoValue(InfoValue.Type.UPGRADABLE, valueEmoji, currentValue, nextValue).buildUpgradable(),
            description
        );
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          String valueEmoji, @NotNull Price<?> currentPrice, @NotNull Price<?> nextPrice,
                                                          String description) {
        return upgradable(title, emoji, valueEmoji, currentPrice.amount(), nextPrice.amount(), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          @NotNull Currency currency, Price<?> currentPrice, Price<?> nextPrice,
                                                          String description) {
        return upgradable(title, emoji, currency.getEmoji(), currentPrice, nextPrice, description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          Number currentValue, Number nextValue,
                                                          String description) {
        return upgradable(title, emoji, "", currentValue, nextValue, description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          @NotNull Price<?> currentPrice, @NotNull Price<?> nextPrice,
                                                          String description) {
        return upgradable(title, emoji, currentPrice.amount(), nextPrice.amount(), description);
    }

    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           String valueEmoji, Number currentValue, Number maxValue,
                                                           String description, boolean includeFull) {
        return new BuildingInfoElement(
            title, emoji,
            new InfoValue(InfoValue.Type.CAPACITABLE, valueEmoji, currentValue, maxValue).buildCapacitable(includeFull),
            description
        );
    }

    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           String valueEmoji, @NotNull Price<?> currentPrice, @NotNull Price<?> maxPrice,
                                                           String description, boolean includeFull) {
        return capacitable(title, emoji, valueEmoji, currentPrice.amount(), maxPrice.amount(), description, includeFull);
    }

    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           @NotNull Currency currency, Price<?> currentPrice, Price<?> maxPrice,
                                                           String description, boolean includeFull) {
        return capacitable(title, emoji, currency.getEmoji(), currentPrice, maxPrice, description, includeFull);
    }

    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           Number currentValue, Number maxValue,
                                                           String description, boolean includeFull) {
        return capacitable(title, emoji, "", currentValue, maxValue, description, includeFull);
    }

    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           @NotNull Price<?> currentPrice, @NotNull Price<?> maxPrice,
                                                           String description, boolean includeFull) {
        return capacitable(title, emoji, currentPrice.amount(), maxPrice.amount(), description, includeFull);
    }


    public String build() {
        return String.format(
            "%s **%s:** %s\n%s %s",
            emoji, title, value, AIR, description
        );
    }
}
