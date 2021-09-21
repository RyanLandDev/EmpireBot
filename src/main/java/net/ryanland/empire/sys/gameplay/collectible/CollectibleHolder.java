package net.ryanland.empire.sys.gameplay.collectible;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.gameplay.collectible.box.impl.DailyBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.box.impl.HourlyBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.box.impl.MemberBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.box.impl.MythicalBoxItem;
import net.ryanland.empire.sys.gameplay.collectible.crystals.PileOfCrystalsReceivable;
import net.ryanland.empire.sys.gameplay.collectible.crystals.PocketOfCrystalsReceivable;
import net.ryanland.empire.sys.gameplay.collectible.potion.DefenseBuildingDamagePotion;
import net.ryanland.empire.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectibleHolder {

    private static final List<Class<? extends Collectible>> COLLECTIBLES = Arrays.asList(

        // Box
        HourlyBoxItem.class,
        DailyBoxItem.class,
        MemberBoxItem.class,
        MythicalBoxItem.class,

        // Item
        PocketOfCrystalsReceivable.class,
        PileOfCrystalsReceivable.class,

        // Potion
        DefenseBuildingDamagePotion.class

    );

    private static final HashMap<String, Class<? extends Collectible>> NAME_COLLECTIBLES = new HashMap<>(
        COLLECTIBLES.stream()
            .collect(Collectors.toMap(collectible -> {
                try {
                    return collectible.getDeclaredConstructor().newInstance().getName();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
    );

    private static final HashMap<Integer, Class<? extends Collectible>> ID_COLLECTIBLES = new HashMap<>(
        COLLECTIBLES.stream()
            .collect(Collectors.toMap(collectible -> {
                try {
                    return collectible.getDeclaredConstructor().newInstance().getId();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
    );

    @SuppressWarnings("unchecked")
    private static final List<Class<Item>> ITEMS = COLLECTIBLES.stream()
        .filter(Item.class::isAssignableFrom)
        .map(clazz -> (Class<Item>) clazz)
        .collect(Collectors.toList());

    private static final HashMap<String, Class<Item>> NAME_ITEMS = new HashMap<>(
        ITEMS.stream()
            .collect(Collectors.toMap(item -> {
                try {
                    return item.getDeclaredConstructor().newInstance().getName();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
    );

    private static final HashMap<Integer, Class<Item>> ID_ITEMS = new HashMap<>(
        ITEMS.stream()
            .collect(Collectors.toMap(item -> {
                try {
                    return item.getDeclaredConstructor().newInstance().getId();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
    );

    public static Collectible get(String name) {
        try {
            return NAME_COLLECTIBLES.get(name).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Collectible get(int id) {
        try {
            return ID_COLLECTIBLES.get(id).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Item getItem(String name) {
        try {
            return NAME_ITEMS.get(name).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Item getItem(int id) {
        try {
            return ID_ITEMS.get(id).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Item findItem(String name) throws CommandException {
        //TODO some kind of way to parse a find string into an item with properties...
        try {
            return ITEMS.stream()
                .map(itemClass -> {
                    try {
                        return itemClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    throw new IllegalArgumentException();
                })
                .filter(item -> StringUtil.convertToFind(item.getFindName())
                    .equals(StringUtil.convertToFind(name)))
                .collect(Collectors.toList())
                .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("An item with the name `" + name + "` was not found.");
        }
    }

}
