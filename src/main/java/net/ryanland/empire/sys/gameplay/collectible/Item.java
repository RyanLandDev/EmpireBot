package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.impl.gameplay.items.InventoryCommand;
import net.ryanland.empire.bot.command.impl.gameplay.items.UseCommand;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.file.serializer.ItemSerializer;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.finder.Checker;
import net.ryanland.empire.sys.finder.CheckerResult;
import net.ryanland.empire.sys.finder.Finder;
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

    /**
     * Sets an item's properties using the provided values appropriately.
     * Not applicable to items without any additional properties.
     * @return this
     */
    default Item parseFindValues(String[] values) throws ArgumentException {
        return this;
    }

    /**
     * How this item should be identified by users when picking one.
     * @see InventoryCommand
     * @see UseCommand
     */
    default String getIdentifier() {
        return String.valueOf(getId());
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
     * Removes this item from the provided profile's inventory.
     * Does not call {@link UserDocument#update}!
     * @param profile The inventory of this profile will be affected.
     */
    default void removeThisFromInventory(Profile profile) {
        List<Item> inventory = new ArrayList<>(profile.getInventory());
        int i = 0;
        for (Item item : inventory) {
            if (item.equals(this)) {
                inventory.remove(i);
                break;
            }
            i++;
        }
        profile.getDocument().setInventory(InventorySerializer.getInstance().serialize(inventory));
    }

    /**
     * Triggered when the player uses this item using the {@link UseCommand}.
     * @param profile The profile associated with this action.
     * @return The {@link PresetBuilder} to send back to the user when the item has been used.
     */
    PresetBuilder use(Profile profile);

}
