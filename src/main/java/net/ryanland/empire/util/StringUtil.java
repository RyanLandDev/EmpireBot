package net.ryanland.empire.util;

import net.ryanland.empire.sys.message.Emojis;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtil implements Emojis {

    public static final SimpleDateFormat STANDARD_DATE_FORMAT =
            new SimpleDateFormat("EEEE dd/MM/yyyy 'at' HH:mm 'UTC'", Locale.US);

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.format(date);
    }

    @Contract(pure = true)
    public static @NotNull String genTrimProofSpaces(int amount) {
        return (" \u200b").repeat(amount);
    }

    public static String getDiscordTimestamp(Date date) {
        return getDiscordTimestamp(date.getTime() / 1000);
    }

    @Contract(pure = true)
    public static @NotNull String getDiscordTimestamp(Long time) {
        return "<t:" + time + ">";
    }

    @NotNull
    public static String getNumberEmoji(@Range(min=1, max=15) int number) {
        switch (number) {
            case 1 -> { return ONE; }
            case 2 -> { return TWO; }
            case 3 -> { return THREE; }
            case 4 -> { return FOUR; }
            case 5 -> { return FIVE; }
            case 6 -> { return SIX; }
            case 7 -> { return SEVEN; }
            case 8 -> { return EIGHT; }
            case 9 -> { return NINE; }
            case 10 -> { return TEN; }
            case 11 -> { return ELEVEN; }
            case 12 -> { return TWELVE; }
            case 13 -> { return THIRTEEN; }
            case 14 -> { return FOURTEEN; }
            case 15 -> { return FIFTEEN; }
        }

        return PUTIN_WALK;
    }

}
