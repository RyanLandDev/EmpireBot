package net.ryanland.empire.sys.gameplay.building;

import net.ryanland.empire.sys.gameplay.building.impl.Building;

import java.util.function.Predicate;

public interface BuildingActionState {

    String getName();

    Predicate<Building> getCheck();
}
