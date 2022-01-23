package net.ryanland.empire.sys.gameplay.combat.troop;

import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.sys.message.Formattable;
import net.ryanland.empire.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Troop implements Formattable {

    private static final List<Class<? extends Troop>> TROOPS = Arrays.asList(
        RecruitTroop.class,
        SkeletonTroop.class,
        GoblinTroop.class,
        SlimeTroop.class,
        GiantTroop.class,
        GoblinTroop.class
    );

    public static List<Class<? extends Troop>> getTroops() {
        return TROOPS;
    }

    public static Troop of(String name) throws ArgumentException {
        try {
            return getTroops().stream()
                .map(troop -> {
                    try {
                        return troop.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    throw new IllegalArgumentException();
                })
                .filter(troop -> troop.getName().equals(name))
                .collect(Collectors.toList())
                .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException("A troop with this name does not exist.");
        }
    }

    public static Troop find(String name) throws ArgumentException {
        try {
            return getTroops().stream()
                .map(troop -> {
                    try {
                        return troop.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    throw new IllegalArgumentException();
                })
                .filter(troop -> StringUtil.convertToFind(troop.getName())
                    .equals(StringUtil.convertToFind(name)))
                .collect(Collectors.toList())
                .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedArgumentException("A troop with this name does not exist.");
        }
    }

    int stage = 1;
    int health = getMaxHealth();

    public Troop set(int stage, int health) {
        return setStage(stage).setHealth(health);
    }

    public Troop set(int stage) {
        return setStage(stage);
    }

    public Troop setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public Troop setHealth(int health) {
        this.health = health;
        return this;
    }

    public TroopBuilder getBuilder() {
        return new TroopBuilder(getClass(), stage, 1);
    }

    public static List<Troop> of(TroopBuilder... troops) {
        return Arrays.stream(troops)
            .map(Troop::of)
            .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    public static List<Troop> of(Class<? extends Troop> type, int stage, int quantity) {
        return of(new TroopBuilder(type, stage, quantity));
    }

    public static List<Troop> of(TroopBuilder troop) {
        List<Troop> result = new ArrayList<>();
        try {
            for (int i = 0; i < troop.quantity; i++) {
                result.add(troop.type.getDeclaredConstructor().newInstance()
                    .setStage(troop.stage)
                );
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    public record TroopBuilder(Class<? extends Troop> type, int stage, int quantity) {

    }

    public void damage(int amount) {
        health -= amount;
    }

    public int getStage() {
        return stage;
    }

    public int getHealth() {
        return health;
    }

    public boolean isActive() {
        return health > 0;
    }

    public abstract String getName();

    public abstract String getEmoji();

    @Override
    public String format() {
        return getEmoji() + " **" + getName() + "**";
    }

    public int getProperty(@NotNull Supplier<Integer> getter, int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        int result = getter.get();
        this.stage = originalStage;

        return result;
    }

    /**
     * The amount of health this troop has by default, otherwise known as the maximum health.
     * In other words, the amount of damage this troop takes before it dies.
     */
    public abstract int getMaxHealth();

    public final int getMaxHealth(int stage) {
        return getProperty(this::getMaxHealth, stage);
    }

    /**
     * The amount of damage this troop deals per hit.
     */
    public abstract int getDamage();

    public final int getDamage(int stage) {
        return getProperty(this::getDamage, stage);
    }

    /**
     * The time between troop hits in milliseconds.
     */
    protected abstract int getSpeed();

    public final int getSpeedInMs() {
        return Math.max(getSpeed(), 100);
    }

    public final int getSpeedInMs(int stage) {
        return getProperty(this::getSpeedInMs, stage);
    }

    /**
     * The time between troop hits in seconds.
     */
    public final double getSpeedInSec() {
        return (double) getSpeedInMs() / 1000;
    }

    /**
     * The amount of XP the user gets if this troop is killed.
     */
    public abstract int getXp();

    public final int getXp(int stage) {
        return getProperty(this::getXp, stage);
    }

    /**
     * The troop rating. This gives an average representation of the troop's power.
     */
    public final int getRating() {
        return 40 - (getDamage() / (getSpeedInMs() / 100) * 35);
    }
}
