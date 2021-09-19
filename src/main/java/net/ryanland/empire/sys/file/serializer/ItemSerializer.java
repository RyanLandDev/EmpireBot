package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import org.jetbrains.annotations.NotNull;

public class ItemSerializer implements Serializer<String, Item> {

    private static final ItemSerializer instance = new ItemSerializer();

    public static ItemSerializer getInstance() {
        return instance;
    }

    @Override
    public String serialize(@NotNull Item toSerialize) {
        return String.valueOf(toSerialize.getId());
    }

    @Override
    public Item deserialize(@NotNull String toDeserialize) {
        return CollectibleHolder.getItem(Integer.parseInt(toDeserialize));
    }
}
