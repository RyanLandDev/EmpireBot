package net.ryanland.empire.sys.finder;

public interface Checker<T> {

    /**
     * Checks if the start of the String matches any of the checks done in this method.
     * @param value The String to check for.
     * @return The found value associated with the beginning of this String.
     * @throws IllegalArgumentException if no match was found.
     */
    CheckerResult<T> accept(String value);
}
