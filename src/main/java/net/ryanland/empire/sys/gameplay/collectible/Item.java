package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.impl.items.UseCommand;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Item} will be stored in the {@link Profile}'s inventory once received with {@link #receive(Profile)}.
 * When the user uses this {@link Item} with {@link UseCommand} the {@link #use(Profile)} method will activate.
 */
public interface Item extends Collectible {

    @Override
    default void receive(Profile profile) {
        List<Item> inventory = new ArrayList<>(profile.getInventory());
        inventory.add(this);

        profile.getDocument().setInventory(InventorySerializer.getInstance().serialize(inventory));
        profile.getDocument().update();
    }

    void use(Profile profile);

}
