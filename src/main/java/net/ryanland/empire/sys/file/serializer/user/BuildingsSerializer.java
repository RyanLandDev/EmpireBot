package net.ryanland.empire.sys.file.serializer.user;

import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

import java.util.List;
import java.util.stream.Collectors;

/**
 *         >> Database Building Structure <<
 *             0 - ID
 *             1 - Stage
 *             2 - Health
 *             3 - (RESOURCE_GENERATOR ONLY) lastCollect
 */
@SuppressWarnings("all")
public class BuildingsSerializer implements Serializer<List<List>, List<Building>> {

    private static final BuildingsSerializer instance = new BuildingsSerializer();

    public static BuildingsSerializer getInstance() {
        return instance;
    }

    @Override
    public List<List> serialize(List<Building> toSerialize) {
        return toSerialize.stream()
                .map(Building::serialize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Building> deserialize(List<List> toDeserialize) {
        return toDeserialize.stream()
                .map(e -> Building.of((Integer) e.get(0)).deserialize(e))
                .collect(Collectors.toList());
    }
}
