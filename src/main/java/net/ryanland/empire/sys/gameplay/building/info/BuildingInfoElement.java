package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.ryanland.empire.sys.util.NumberUtil.format;

public record BuildingInfoElement(String title, String emoji, String value, String description) implements Emojis {

    public BuildingInfoElement(String title, String emoji, int value, String description) {
        this(title, emoji, String.valueOf(value), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji,
                                                          String valueEmoji, Number currentValue, Number nextValue,
                                                          String description) {
        return new BuildingInfoElement(title, emoji, getUpgradableValue(valueEmoji, currentValue, nextValue), description);
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

    private static String getUpgradableValue(@NotNull String valueEmoji,
                                             Number currentValue, Number nextValue) {
        return String.format("%s%s", valueEmoji.isEmpty() ? "" : valueEmoji + " ",
                Objects.equals(currentValue, nextValue) ? format(currentValue) :
                        String.format("%s %s *%s*", format(currentValue), ARROW_RIGHT, format(nextValue))
        );
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           String valueEmoji, Number currentValue, Number maxValue,
                                                           String description) {
        return new BuildingInfoElement(title, emoji, getCapacitableValue(valueEmoji, currentValue, maxValue), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           String valueEmoji, @NotNull Price<?> currentPrice, @NotNull Price<?> maxPrice,
                                                           String description) {
        return capacitable(title, emoji, valueEmoji, currentPrice.amount(), maxPrice.amount(), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static BuildingInfoElement capacitable(String title, String emoji,
                                                  @NotNull Currency currency, Price<?> currentPrice, Price<?> maxPrice,
                                                  String description) {
        return capacitable(title, emoji, currency.getEmoji(), currentPrice, maxPrice, description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           Number currentValue, Number maxValue,
                                                           String description) {
        return new BuildingInfoElement(title, emoji, getCapacitableValue("", currentValue, maxValue), description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement capacitable(String title, String emoji,
                                                           @NotNull Price<?> currentPrice, @NotNull Price<?> maxPrice,
                                                           String description) {
        return new BuildingInfoElement(title, emoji, getCapacitableValue("", currentPrice.amount(), maxPrice.amount()), description);
    }

    private static String getCapacitableValue(String valueEmoji,
                                              Number currentValue, Number maxValue) {
        return String.format("%s%s / %s", valueEmoji.isEmpty() ? "" : valueEmoji + " ", format(currentValue), format(maxValue));
    }

    public String build() {
        return String.format(
                "%s **%s:** %s\n%s %s",
                emoji, title, value, AIR, description
        );
    }
}
