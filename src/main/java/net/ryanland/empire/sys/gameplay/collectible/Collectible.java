package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.sys.file.database.MongoDB;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.message.Formattable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface Collectible extends Formattable {

    /**
     * Name of the {@link Collectible}
     */
    String getName();

    /**
     * Head name of the {@link Collectible}
     */
    String getHeadName();

    /**
     * Associated emoji of the {@link Collectible}
     */
    String getEmoji();

    /**
     * Format the {@link Collectible} using {@link #getName} and {@link #getEmoji}
     */
    @Override
    default String format() {
        return getEmoji() + " **" + getName() + (getHeadName() == null || getHeadName().isEmpty() ? "" : " " + getHeadName()) + "**";
    }

    /**
     * The ID to use when storing this {@link Collectible} in the {@link MongoDB}
     */
    int getId();

    /**
     * Code executed when the {@link Collectible} is received
     *
     * @return Formatted received {@link String}
     */
    String receive(Profile profile);

}

