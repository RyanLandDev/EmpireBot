package net.ryanland.empire.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static final SimpleDateFormat STANDARD_DATE_FORMAT =
            new SimpleDateFormat("EEEE dd/MM/yyyy 'at' HH:mm 'UTC'");
    public static final SimpleDateFormat RELATIVE_DATE_FORMAT =
            new SimpleDateFormat("D'd' H'h' m'm' s's'");

    public static final long TIMEZONE_OFFSET = -3600000;

    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.format(date);
    }

    public static String formatRelative(Date date) {
        String formatted = RELATIVE_DATE_FORMAT.format(new Date(date.getTime() + TIMEZONE_OFFSET));

        // Get the first number (the day) and decrease it by 1
        Matcher matcher = Pattern.compile("^\\d+").matcher(formatted);
        while (matcher.find()) {
            formatted = formatted.replaceFirst("^\\d+",
                    String.valueOf(Integer.parseInt(matcher.group()) - 1));
        }

        // This regex will remove all spaces and leading 0s
        return formatted.replaceAll("^((^| )0[^\\d ]+)*| ", "");
    }

    public static String getDiscordTimestamp(Date date) {
        return getDiscordTimestamp(date.getTime() / 1000);
    }

    @Contract(pure = true)
    public static @NotNull String getDiscordTimestamp(Long time) {
        return "<t:" + time + ">";
    }
}
