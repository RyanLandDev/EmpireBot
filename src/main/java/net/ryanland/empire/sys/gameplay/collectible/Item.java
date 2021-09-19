package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.impl.gameplay.items.UseCommand;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.file.serializer.ItemSerializer;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
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
     * jfojweoij//TODO
     * @param profile
     * @return
     */
    PresetBuilder use(Profile profile);

}
