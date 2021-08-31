package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;

import java.util.ArrayList;
import java.util.List;

public interface Collectible {

    /*
        Name of the Collectible
     */
    String getName();

    /*
        Head name of the Collectible
     */
    String getHeadName();

    /*
        Associated emoji of the Collectible
     */
    String getEmoji();

    /*
        Format the Collectible using its name and emoji
     */
    default String format() {
        return getEmoji() + " **" + getName() + (getHeadName() == null || getHeadName().isEmpty() ? "" : " " + getHeadName()) + "**";
    }

    /*
        The ID to use when storing this Collectible in the database
     */
    int getId();

    /*
        Code executed when the Collectible is received
     */
    void receive(Profile profile);

}

