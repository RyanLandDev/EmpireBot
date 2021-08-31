package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class InventorySerializer implements Serializer<List<Integer>, List<Item>> {

    private static final InventorySerializer instance = new InventorySerializer();

    public static InventorySerializer getInstance() {
        return instance;
    }

    @Override
    public List<Integer> serialize(@NotNull List<Item> toSerialize) {
        return toSerialize.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> deserialize(@NotNull List<Integer> toDeserialize) {
        return toDeserialize.stream()
                .map(CollectibleHolder::getItem)
                .collect(Collectors.toList());
    }
}
