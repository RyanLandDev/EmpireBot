package net.ryanland.empire.sys.gameplay.action;

import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;

/**
 * Implementation of the {@link Action} class for actions that can be affected by potions.
 */
public abstract class BuffedAction<R> implements Action<R> {

    protected float multiplier = Potion.Multiplier.NORMAL.getMultiplier();

    @Override
    public R perform() {
        //TODO calculate multiplier
        return run();
    }

    public abstract Potion getPotion();
}
