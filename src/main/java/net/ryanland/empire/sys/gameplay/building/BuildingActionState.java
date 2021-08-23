package net.ryanland.empire.sys.gameplay.building;

import net.ryanland.empire.sys.gameplay.building.impl.Building;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.function.Predicate;

public interface BuildingActionState {

    String getName();

    Predicate<Building> getCheck();

    static <T extends Enum<T> & BuildingActionState> @NotNull T get(Building building, Class<T> anEnum) {
        EnumSet<T> enumSet = EnumSet.allOf(anEnum);

        for (T state : enumSet) {
            if (!state.getCheck().test(building)) {
                return state;
            }
        }

        throw new IllegalArgumentException();
    }
}
