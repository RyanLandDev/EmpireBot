package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class InventorySerializer implements Serializer<List<String>, List<Item>> {

    private static final InventorySerializer instance = new InventorySerializer();

    public static InventorySerializer getInstance() {
        return instance;
    }

    @Override
    public List<String> serialize(@NotNull List<Item> toSerialize) {
        return toSerialize.stream()
            .map(Item::serialize)
            .collect(Collectors.toList());
    }

    @Override
    public List<Item> deserialize(@NotNull List<String> toDeserialize) {
        return toDeserialize.stream()
            .map(item -> {
                String[] data = item.split(";");
                return CollectibleHolder.getItem(Integer.parseInt(data[0]))
                    .deserialize(item);
            })
            .collect(Collectors.toList());
    }
}
