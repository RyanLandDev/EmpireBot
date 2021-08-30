package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class InventorySerializer implements Serializer<List<Integer>, List<Collectible>> {

    private static final InventorySerializer instance = new InventorySerializer();

    public static InventorySerializer getInstance() {
        return instance;
    }

    @Override
    public List<Integer> serialize(@NotNull List<Collectible> toSerialize) {
        return toSerialize.stream()
                .map(Collectible::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Collectible> deserialize(@NotNull List<Integer> toDeserialize) {
        return toDeserialize.stream()
                .map(CollectibleHolder::get)
                .collect(Collectors.toList());
    }
}
