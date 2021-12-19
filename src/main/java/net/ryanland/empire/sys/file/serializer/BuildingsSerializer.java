package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * >> Database Building Structure <<
 * 0 - ID
 * 1 - Stage
 * 2 - Health
 * 3 - (RESOURCE_GENERATOR ONLY) lastCollect
 */
@SuppressWarnings("all")
public class BuildingsSerializer implements Serializer<List<List>, List<Building>> {

    private static final BuildingsSerializer instance = new BuildingsSerializer();

    public static BuildingsSerializer getInstance() {
        return instance;
    }

    @Override
    public List<List> serialize(@NotNull List<Building> toSerialize) {
        return toSerialize.stream()
            .map(Building::serialize)
            .collect(Collectors.toList());
    }

    @Override
    public List<Building> deserialize(@NotNull List<List> toDeserialize) {
        return deserialize(toDeserialize, null);
    }

    public List<Building> deserialize(@NotNull List<List> toDeserialize, @Nullable Profile profile) {
        AtomicInteger i = new AtomicInteger();

        return toDeserialize.stream()
            .map(e -> {
                i.getAndIncrement();

                return Building.of((Integer) e.get(0))
                        .deserialize(e)
                        .setLayer(i.get())
                        .setProfile(profile);
                }
            )
            .collect(Collectors.toList());
    }
}
