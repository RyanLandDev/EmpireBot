package net.ryanland.empire.sys.gameplay.collectible.perk;

import net.ryanland.empire.sys.file.database.MongoDB;
import net.ryanland.empire.sys.gameplay.collectible.Collectible;

import java.time.Duration;
import java.util.Date;
//TODO
public class BuilderPerk extends Perk {
    public BuilderPerk(Date expires) {
        super(expires);
    }

    /**
     * Name of the {@link Collectible}
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Associated emoji of the {@link Collectible}
     */
    @Override
    public String getEmoji() {
        return null;
    }

    /**
     * The ID to use when storing this {@link Collectible} in the {@link MongoDB}
     */
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return null;
    }
}
