package net.ryanland.empire.sys.gameplay.currency;

import net.ryanland.empire.sys.util.NumberUtil;
import org.jetbrains.annotations.NotNull;

public record Price<T extends Number>(Currency currency, T amount) {

    public String format() {
        return format(false);
    }

    public String format(boolean underlined) {
        return String.format("%s %3$s%s%3$s", currency.getEmoji(), formatAmount(), underlined ? "__" : "");
    }

    public @NotNull String formatAmount() {
        return NumberUtil.format(amount);
    }

}
