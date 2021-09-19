package net.ryanland.empire.sys.gameplay.collectible.potion;

import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.PotionSerializer;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Potion implements Item {

    @Override
    public Serializer<String, Item> getSerializer() {
        return PotionSerializer.getInstance();
    }

    @Override
    public String getHeadName() {
        return "Potion";
    }

    @Override
    public PresetBuilder use(Profile profile) {
        return null;//TODO
    }

    private Multiplier multiplier;
    private Length length;
    private Scope scope;
    private Date expires;

    public Potion setExpires(Date expires) {
        this.expires = expires;
        return this;
    }

    /**
     * Returns the {@link Date} of when this potion will expire.
     * This value will be {@link Nullable} if the potion is still in the profile's inventory,
     * and {@link NotNull} if in the profile's active potions' container.
     */
    public Date getExpires() {
        return expires;
    }

    public Potion set(Multiplier multiplier, Length length, Scope scope) {
        return setMultiplier(multiplier)
            .setLength(length)
            .setScope(scope);
    }

    public Potion setMultiplier(Multiplier multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public Multiplier getMultiplier() {
        return multiplier;
    }

    public enum Multiplier {

        NORMAL(-1, "1x", 1f),
        ONE_AND_A_HALF(1, "1.5x", 1.5f),
        DOUBLE(3, "2x", 2f),
        TWO_AND_A_HALF(5, "2.5x", 2.5f),
        ;

        private final int id;
        private final String name;
        private final float multiplier;

        Multiplier(int id, String name, float multiplier) {
            this.id = id;
            this.name = name;
            this.multiplier = multiplier;
        }

        private final static HashMap<Integer, Multiplier> ID_MULTIPLIERS = new HashMap<>(
            Arrays.stream(values())
                .collect(Collectors.toMap(Multiplier::getId, Function.identity()))
        );

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public float getMultiplier() {
            return multiplier;
        }

        public static Multiplier of(int id) {
            return ID_MULTIPLIERS.get(id);
        }
    }

    public Potion setLength(Length length) {
        this.length = length;
        return this;
    }

    public Length getLength() {
        return length;
    }

    public enum Length {

        FIVE_MINUTES(5, "5min", Duration.ofMinutes(5)),
        TWENTY_MINUTES(20, "20min", Duration.ofMinutes(20))
        ;

        private final int id;
        private final String name;
        private final Duration duration;

        Length(int id, String name, Duration duration) {
            this.id = id;
            this.name = name;
            this.duration = duration;
        }

        private final static HashMap<Integer, Length> ID_LENGTHS = new HashMap<>(
            Arrays.stream(values())
                .collect(Collectors.toMap(Length::getId, Function.identity()))
        );

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Duration getDuration() {
            return duration;
        }

        public static Length of(int id) {
            return ID_LENGTHS.get(id);
        }
    }

    public Potion setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public Scope getScope() {
        return scope;
    }

    public enum Scope {

        USER(0, "User"),
        GUILD(1, "Server"),
        GLOBAL(2, "Global")
        ;

        private final int id;
        private final String name;

        Scope(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private final static HashMap<Integer, Scope> ID_SCOPES = new HashMap<>(
            Arrays.stream(values())
                .collect(Collectors.toMap(Scope::getId, Function.identity()))
        );

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static Scope of(int id) {
            return ID_SCOPES.get(id);
        }
    }

}
