package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.sys.gameplay.collectible.box.HourlyBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.crystals.PileOfCrystalsReceivable;
import net.ryanland.empire.sys.gameplay.collectible.crystals.PocketOfCrystalsReceivable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectibleHolder {

    @SuppressWarnings("all")
    private static final Collectible[] COLLECTIBLES = new Collectible[]{

            // Box
            new HourlyBoxItem(),

            // Item
            new PocketOfCrystalsReceivable(),
            new PileOfCrystalsReceivable()

    };

    private static final HashMap<String, Collectible> NAME_COLLECTIBLES = new HashMap<>(
            Arrays.stream(COLLECTIBLES)
                    .collect(Collectors.toMap(Collectible::getName, Function.identity()))
    );

    private static final HashMap<Integer, Collectible> ID_COLLECTIBLES = new HashMap<>(
            Arrays.stream(COLLECTIBLES)
                    .collect(Collectors.toMap(Collectible::getId, Function.identity()))
    );

    private static final HashMap<String, Item> NAME_ITEMS = new HashMap<>(
            Arrays.stream(COLLECTIBLES)
                    .filter(c -> c instanceof Item)
                    .collect(Collectors.toMap(Collectible::getName, c -> (Item) c))
    );

    private static final HashMap<Integer, Item> ID_ITEMS = new HashMap<>(
            Arrays.stream(COLLECTIBLES)
                    .filter(c -> c instanceof Item)
                    .collect(Collectors.toMap(Collectible::getId, c -> (Item) c))
    );

    public static Collectible get(String name) {
        return NAME_COLLECTIBLES.get(name);
    }

    public static Collectible get(int id) {
        return ID_COLLECTIBLES.get(id);
    }

    public static Item getItem(String name) {
        return NAME_ITEMS.get(name);
    }

    public static Item getItem(int id) {
        return ID_ITEMS.get(id);
    }

}
