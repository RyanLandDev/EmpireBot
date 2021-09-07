package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.gameplay.collectible.box.DailyBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.box.HourlyBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.box.MemberBoxItem;
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
            new DailyBoxItem(),
            new MemberBoxItem(),

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

    private static final Item[] ITEMS = Arrays.stream(COLLECTIBLES)
            .filter(c -> c instanceof Item)
            .map(c -> (Item) c)
            .toArray(Item[]::new);

    private static final HashMap<String, Item> NAME_ITEMS = new HashMap<>(
            Arrays.stream(ITEMS)
                    .collect(Collectors.toMap(Item::getName, Function.identity()))
    );

    private static final HashMap<Integer, Item> ID_ITEMS = new HashMap<>(
            Arrays.stream(ITEMS)
                    .collect(Collectors.toMap(Item::getId, Function.identity()))
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

    public static Item findItem(String name) throws CommandException {
        try {
            return Arrays.stream(ITEMS)
                    .filter(item -> item.getName()
                            .replaceAll("[ _-]", "")
                            .equalsIgnoreCase(name
                                    .replaceAll("[ _-]", "")))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("An item with the name `" + name + "` was not found.");
        }
    }

}
