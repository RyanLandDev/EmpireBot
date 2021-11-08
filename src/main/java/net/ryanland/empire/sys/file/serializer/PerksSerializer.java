package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.perk.Perk;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("all")
public class PerksSerializer implements Serializer<List<List>, List<Perk>> {

    private static PerksSerializer instance = new PerksSerializer();

    public static PerksSerializer getInstance() {
        return instance;
    }

    @Override
    public List<List> serialize(@NotNull List<Perk> toSerialize) {
        return null;//TODO
    }

    @Override
    public List<Perk> deserialize(@NotNull List<List> toDeserialize) {
        return null;
    }
}
