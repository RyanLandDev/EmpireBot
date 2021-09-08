package net.ryanland.empire.sys.gameplay.combat.troop;

import net.ryanland.empire.sys.message.Formattable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Troop implements Formattable {

    @SuppressWarnings("all")
    private final static Troop[] TROOPS = new Troop[]{
        new RecruitTroop()
    };

    private final static Map<String, Troop> NAME_TROOPS = new HashMap<>(
        Arrays.stream(TROOPS)
            .collect(Collectors.toMap(Troop::getName, Function.identity()))
    );

    public static Troop of(String name) {
        return NAME_TROOPS.get(name);
    }

    int stage;
    int quantity = 1;

    public Troop setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public Troop setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Troop set(int stage, int quantity) {
        return setStage(stage).setQuantity(quantity);
    }

    public int getStage() {
        return stage;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract String getName();

    public abstract String getEmoji();

    @Override
    public String format() {
        return getEmoji() + " **" + getName() + "**";
    }

    /**
     * The amount of health this troop has.
     * In other words, the amount of damage this troop takes before it dies.
     */
    public abstract int getHealth();

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
