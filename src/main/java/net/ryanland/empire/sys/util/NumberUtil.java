package net.ryanland.empire.sys.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {


    public static String format(Integer number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }
}
