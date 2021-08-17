package net.ryanland.empire.sys.gameplay.building.info;

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
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji, String valueEmoji, Number currentValue, Number nextValue, String description) {
        return new BuildingInfoElement(title, emoji, getUpgradableDescription(valueEmoji, currentValue, nextValue), description);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji, String valueEmoji, @NotNull Price<?> currentPrice, @NotNull Price<?> nextPrice, String description) {
        return upgradable(title, emoji, valueEmoji, currentPrice.amount(), nextPrice.amount(), description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji, Number currentValue, Number nextValue, String description) {
        return upgradable(title, emoji, "", currentValue, nextValue, description);
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull BuildingInfoElement upgradable(String title, String emoji, @NotNull Price<?> currentPrice, @NotNull Price<?> nextPrice, String description) {
        return upgradable(title, emoji, currentPrice.amount(), nextPrice.amount(), description);
    }

    private static String getUpgradableDescription(@NotNull String valueEmoji, Number currentValue, Number nextValue) {
        return String.format("%s%s", valueEmoji.isEmpty() ? "" : valueEmoji + " ",
                Objects.equals(currentValue, nextValue) ? format(currentValue) :
                        String.format("%s %s *%s*", format(currentValue), ARROW_RIGHT, format(nextValue))
        );
    }

    public String build() {
        return String.format(
                "%s **%s:** %s\n%s %s",
                emoji, title, value, AIR, description
        );
    }
}
