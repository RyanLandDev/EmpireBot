package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.impl.gameplay.items.UseCommand;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.file.serializer.ItemSerializer;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Item} will be stored in the {@link Profile}'s inventory once received with {@link #receive(Profile)}.
 * When the user uses this {@link Item} with {@link UseCommand} the {@link #use(Profile)} method will activate.
 * The returned {@link PresetBuilder} of {@link #use(Profile)} is sent to the user upon triggering the {@link UseCommand}.
 */
public interface Item extends Collectible, Serializable, Serializer<String, Item> {

    default String serialize() {
        return serialize(this);
    }

    /**
     * The name to use in the {@link CollectibleHolder#findItem(String)} method.
     * Note: This string will go through the {@link StringUtil#convertToFind(String)} function first.
     */
    default String getFindName() {
        return getName();
    }

    /**
     * Checks if this item is exactly equal to another using its ID.
     * Override this method if the item has more properties!
     * @param item The item to compare to
     * @return A boolean indicating the result
     */
    default boolean equals(Item item) {
        return getId() == item.getId();
    }

    /**
     * Checks if the type of this item is equal to another item using its ID.
     * Override this method if the item has more properties!
     * This method should not check for properties that could change per instance, such as an expiration date.
     * @param item The item to compare to
     * @return A boolean indicating the result
     */
    default boolean typeEquals(Item item) {
        return getId() == item.getId();
    }

    @Override
    default String serialize(@NotNull Item item) {
        return getSerializer().serialize(item);
    }

    @Override
    default Item deserialize(@NotNull String data) {
        return getSerializer().deserialize(data);
    }

    /**
     * The serializer associated with this type of {@link Item}
     */
    default Serializer<String, Item> getSerializer() {
        return ItemSerializer.getInstance();
    }

    @Override
    default String receive(Profile profile) {
        List<Item> inventory = new ArrayList<>(profile.getInventory());
        inventory.add(this);

        profile.getDocument().setInventory(InventorySerializer.getInstance().serialize(inventory));
        profile.getDocument().update();

        return format();
    }

    /**
     * Triggered when the player uses this item using the {@link UseCommand}.
     * @param profile The profile associated with this action.
     * @return The {@link PresetBuilder} to send back to the user when the item has been used.
     */
    PresetBuilder use(Profile profile);

}
