package net.ryanland.empire.sys.file.serializer.user;

import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.impl.Building;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class BuildingsSerializer implements Serializer<List<List>, List<Building>> {

    private static final BuildingsSerializer instance = new BuildingsSerializer();

    public static BuildingsSerializer getInstance() {
        return instance;
    }

    /*
        >> Database Building Structure <<
            0 - ID
            1 - Stage
            2 - Health
            3 - (RESOURCE_GENERATOR ONLY) lastCollect
   */

    @Override
    public List<List> serialize(List<Building> toSerialize) {
        return toSerialize.stream()
                .map(e -> Arrays.asList(e.getId(), e.getStage(), e.getHealth(), e.getLastCollect()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Building> deserialize(List<List> toDeserialize) {
        return toDeserialize.stream()
                .map(e -> Building.of((int) e.get(0)).init((int) e.get(1), (int) e.get(2), (Date) e.get(3)))
                .collect(Collectors.toList());
    }
}
