package net.ryanland.empire.sys.gameplay.building.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingInfoBuilder {

    private final List<BuildingInfoSegment> segments = new ArrayList<>();

    public BuildingInfoBuilder addSegment(BuildingInfoSegment segment) {
        segments.add(segment);
        return this;
    }

    public BuildingInfoBuilder addSegments(BuildingInfoSegment... segments) {
        this.segments.addAll(Arrays.asList(segments));
        return this;
    }

    public BuildingInfoBuilder addSegment(BuildingInfoSegmentBuilder segment) {
        segments.add(segment.build());
        return this;
    }

    public BuildingInfoBuilder addSegments(BuildingInfoSegmentBuilder... segments) {
        this.segments.addAll(Arrays.stream(segments)
            .map(BuildingInfoSegmentBuilder::build)
            .collect(Collectors.toList()));
        return this;
    }

    public BuildingInfo build() {
        return new BuildingInfo(segments);
    }
}
