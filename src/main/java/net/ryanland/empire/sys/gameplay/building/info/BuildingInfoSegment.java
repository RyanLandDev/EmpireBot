package net.ryanland.empire.sys.gameplay.building.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingInfoSegment extends ArrayList<BuildingInfoElement> {

    public BuildingInfoSegment(List<BuildingInfoElement> elements) {
        addAll(elements);
    }

    public BuildingInfoSegment(BuildingInfoElement[] elements) {
        this(Arrays.asList(elements));
    }

    public String build() {
        return stream()
            .map(BuildingInfoElement::build)
            .collect(Collectors.joining("\n\n"));
    }

}
