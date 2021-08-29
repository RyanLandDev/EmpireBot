package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.currency.Price;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ResourceStorageBuilding extends ResourceBuilding {

    @Override
    public BuildingType getType() {
        return BuildingType.RESOURCE_STORAGE;
    }

    @Override
    public Price<Integer> getHolding() {
        List<ResourceStorageBuilding> buildings = getProfile().getBuildings().stream()
                .filter(b -> b.isUsable()
                        && b.getType() == BuildingType.RESOURCE_STORAGE
                        && ((ResourceStorageBuilding) b).getEffectiveCurrency() == getEffectiveCurrency())
                .map(b -> (ResourceStorageBuilding) b)
                .collect(Collectors.toList());

        int toStore = getEffectiveCurrency().get(getProfile()).amount();

        for (ResourceStorageBuilding building : buildings) {

            int capacity = building.getCapacity().amount();
            toStore = toStore - capacity;

            if (layer == building.getLayer()) {
                if (toStore < 0) {
                    return new Price<>(getEffectiveCurrency(), toStore + capacity);
                } else {
                    return new Price<>(getEffectiveCurrency(), capacity);
                }
            }

            if (toStore < 0) {
                return new Price<>(getEffectiveCurrency(), 0);
            }
        }

        return new Price<>(getEffectiveCurrency(), 0);
    }
}
