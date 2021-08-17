package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.gameplay.building.impl.Building;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BuildingInfoSegmentBuilder extends BuildingInfoBuilder {

    private final List<BuildingInfoElement> elements = new ArrayList<>();

    public BuildingInfoSegmentBuilder addElement(BuildingInfoElement element) {
        elements.add(element);
        return this;
    }

    public BuildingInfoSegmentBuilder addElements(BuildingInfoElement... elements) {
        this.elements.addAll(Arrays.asList(elements));
        return this;
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, String value, String description) {
        elements.add(new BuildingInfoElement(title, emoji, value, description));
        return this;
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, Function<Building, String> valueFunction, String description) {
        return addElement(title, emoji, valueFunction.apply(getBuilding()), description);
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, int value, String description) {
        return addElement(title, emoji, String.valueOf(value), description);
    }

    public BuildingInfoSegment buildSegment() {
        return new BuildingInfoSegment(elements);
    }
}
