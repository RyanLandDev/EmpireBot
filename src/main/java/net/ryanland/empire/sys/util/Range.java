package net.ryanland.empire.sys.util;

import java.lang.annotation.*;

/**
 * Indicates this parameter must be within this range.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Range {

    int min();
    int max();
}
