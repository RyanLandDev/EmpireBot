package net.ryanland.empire.sys.gameplay.action;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;

/**
 * Implementation of the {@link Action} class for actions that can be affected by potions.
 */
public abstract class BuffedAction<R> implements Action<R> {

    protected float multiplier = Potion.Multiplier.NORMAL.getMultiplicand();

    @Override
    public R perform(Profile profile) {
        multiplier = (float) profile.getPotions().stream()
            .filter(potion -> potion.idEquals(getPotion()))
            .mapToDouble(potion -> potion.getMultiplier().getMultiplicand())
            .reduce(1, (a, b) -> a * b);
        return run(profile);
    }

    public abstract Potion getPotion();
}
