package net.ryanland.empire.sys.gameplay.combat.troop;

import net.ryanland.empire.sys.message.Formattable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Troop implements Formattable {

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

    /**
     * The amount of health this troop has by default, otherwise known as the maximum health.
     * In other words, the amount of damage this troop takes before it dies.
     */
    public abstract int getMaxHealth();

    /**
     * The amount of damage this troop deals per hit.
     */
    public abstract int getDamage();

    /**
     * The time between troop hits in milliseconds.
     */
    public abstract int getSpeedInMs();

    /**
     * The time between troop hits in seconds.
     */
    public final int getSpeedInSec() {
        return getSpeedInMs() * 1000;
    }

    /**
     * The amount of XP the user gets if this troop is killed.
     */
    public abstract int getXp();

    /**
     * The troop rating. This gives an average representation of the troop's power.
     */
    public final int getRating() {
        return 40 - (getDamage() / (getSpeedInMs() / 100) * 35);
    }
}
