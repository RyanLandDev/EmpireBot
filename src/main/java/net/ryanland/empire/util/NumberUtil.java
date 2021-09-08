package net.ryanland.empire.util;

import net.ryanland.empire.sys.message.Emojis;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    /**
     * Equivalent of {@link NumberFormat#format(long)} with {@link Locale}{@code .US}.
     *
     * @param number Number to format.
     * @return The formatted number string.
     */
    public static String format(Number number) {
        if (number == null) {
            return null;
        }

        return NumberFormat.getInstance(Locale.US).format(number);
    }

    public static @NotNull String progressBar(int size, float current, float max) {
        float progress = current / max;
        int filledBlocks = (int) Math.floor(progress * size);
        int unfilledBlocks = size - filledBlocks;

        return "[" +
            Emojis.FILLED_BLOCK.repeat(
                clamp(filledBlocks, size)) +
            Emojis.UNFILLED_BLOCK.repeat(
                clamp(unfilledBlocks, size))
            + "]";
    }

    public static Integer of(String formattedNumber) {
        return Integer.parseInt(formattedNumber.replaceAll(",", ""));
    }

    public static int clamp(int value, int max) {
        return clamp(value, 0, max);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static boolean inRange(int number, int min, int max) {
        return number >= min && number <= max;
    }

    public static boolean withinRange(int number, int min, int max) {
        return number > min && number < max;
    }

}
