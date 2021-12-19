package net.ryanland.empire.sys.gameplay.collectible.potion;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.file.serializer.PotionSerializer;
import net.ryanland.empire.sys.file.serializer.PotionsSerializer;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;
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
        if (getScope() != Scope.USER) {
            throw new IllegalArgumentException("Unimplemented.");
        }

        removeThisFromInventory(profile);

        List<Potion> userPotions = new ArrayList<>(profile.getUserPotions());
        setExpires(new Date(System.currentTimeMillis() + getLength().getDuration().toMillis()));
        userPotions.add(this);
        profile.getDocument().setPotions(PotionsSerializer.getInstance().serialize(userPotions));
        profile.getDocument().update();

        return new PresetBuilder(PresetType.SUCCESS,
            "Used " + format(), "Potion");
    }

    @Override
    public String format() {
        return "%s **%s%s %s %s (%s)**".formatted(
            getEmoji(),
            getScope().isIncluded() ? getScope().getName() : "",
            getMultiplier().getName(),
            getName(),
            getHeadName(),
            getLength().getName()
        );
    }

    @Override
    public final boolean typeEquals(Item item) {
        if (!(item instanceof Potion potion))
            return false;

        return getId() == potion.getId() &&
            getMultiplier() == potion.getMultiplier() &&
            getLength() == potion.getLength() &&
            getScope() == potion.getScope();
    }

    @Override
    public final boolean equals(Item item) {
        if (!(item instanceof Potion potion))
            return false;

        return getId() == potion.getId() &&
            getMultiplier() == potion.getMultiplier() &&
            getLength() == potion.getLength() &&
            getScope() == potion.getScope() &&
            getExpires() == potion.getExpires();
    }

    @Override
    public Item parseFindValues(String[] values) throws ArgumentException {
        Integer[] data = Arrays.stream(values)
            .map(Integer::parseInt)
            .toArray(Integer[]::new);

        if (data.length < 4) {
            throw new MalformedArgumentException("Please use the full ID!\nExample: `/use 51 3 5 1`.");
        }

        setMultiplier(Multiplier.of(data[1]));
        setLength(Length.of(data[2]));
        setScope(Scope.of(data[3]));

        return this;
    }

    @Override
    public String getIdentifier() {
        return "%s %s %s %s".formatted(
            getId(), getMultiplier().getId(), getLength().getId(), getScope().getId()
        );
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
     * Returns the {@link Date} of when this potion will expire.<br>
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

        NORMAL(1, "1x", 1f),
        ONE_AND_A_HALF(3, "1.5x", 1.5f),
        DOUBLE(5, "2x", 2f),
        TWO_AND_A_HALF(7, "2.5x", 2.5f),
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

        public float getMultiplicand() {
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

        USER(1, "User", false),
        CLAN(2, "Clan"),
        GLOBAL(3, "Global")
        ;

        private final int id;
        private final String name;
        private final boolean include;

        Scope(int id, String name, boolean include) {
            this.id = id;
            this.name = name;
            this.include = include;
        }

        Scope(int id, String name) {
            this(id, name, true);
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

        /**
         * If this scope's name should be included in certain strings
         */
        public boolean isIncluded() {
            return include;
        }

        public static Scope of(int id) {
            return ID_SCOPES.get(id);
        }
    }

}
