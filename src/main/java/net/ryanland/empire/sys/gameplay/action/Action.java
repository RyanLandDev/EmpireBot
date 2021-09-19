package net.ryanland.empire.sys.gameplay.action;

/**
 * Helper class to perform actions and optionally return a result.
 * @see BuffedAction
 */
public interface Action<R> {

    /**
     * This method should be called to perform an action
     */
    R perform();

    /**
     * This method should be called inside the {@link #perform()} method
     */
    R run();
}
