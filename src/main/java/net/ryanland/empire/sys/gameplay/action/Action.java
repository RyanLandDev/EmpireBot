package net.ryanland.empire.sys.gameplay.action;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;

/**
 * Helper class to perform actions and optionally return a result.
 * @see BuffedAction
 */
public interface Action<R> {

    /**
     * This method should be called to perform an action
     */
    R perform(Profile profile);

    /**
     * This method should be called inside the {@link #perform(Profile)} method
     */
    R run(Profile profile);
}
