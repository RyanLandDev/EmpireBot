package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.gameplay.building.impl.Building;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BuildingInfoSegmentBuilder extends BuildingInfoBuilder {

    private final List<BuildingInfoElement> elements = new ArrayList<>();

    public BuildingInfoSegmentBuilder addElement(BuildingInfoElement element) {
        elements.add(element);
        return this;
    }

    public BuildingInfoSegmentBuilder insertElement(int index, BuildingInfoElement element) {
        elements.add(index, element);
        return this;
    }

    public BuildingInfoSegmentBuilder addElements(BuildingInfoElement... elements) {
        return addElements(Arrays.asList(elements));
    }

    public BuildingInfoSegmentBuilder addElements(List<BuildingInfoElement> elements) {
        this.elements.addAll(elements);
        return this;
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, String value, String description) {
        elements.add(new BuildingInfoElement(title, emoji, value, description));
        return this;
    }

    public BuildingInfoSegmentBuilder insertElement(int index, String title, String emoji, String value, String description) {
        elements.add(index, new BuildingInfoElement(title, emoji, value, description));
        return this;
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, @NotNull Function<Building, String> valueFunction, String description) {
        return addElement(title, emoji, valueFunction.apply(getBuilding()), description);
    }

    public BuildingInfoSegmentBuilder addElement(int index, String title, String emoji, @NotNull Function<Building, String> valueFunction, String description) {
        return insertElement(index, title, emoji, valueFunction.apply(getBuilding()), description);
    }

    public BuildingInfoSegmentBuilder addElement(String title, String emoji, int value, String description) {
        return addElement(title, emoji, String.valueOf(value), description);
    }

    public BuildingInfoSegmentBuilder insertElement(int index, String title, String emoji, int value, String description) {
        return insertElement(index, title, emoji, String.valueOf(value), description);
    }

    public BuildingInfoSegment buildSegment() {
        return new BuildingInfoSegment(elements);
    }
}
