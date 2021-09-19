package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Database Structure
 *
 * ID;Multiplier;Length;Scope
 *
 * Multiplier
 * 1 = 1.5x
 * 3 = 2x
 * 5 = 2.5x
 *
 * Length
 * 5 = 5min
 * 20 = 20min
 *
 * Scope
 * 0 = User
 * 1 = Guild
 * 2 = Global
 */
public class PotionSerializer implements Serializer<String, Item> {

    private static final PotionSerializer instance = new PotionSerializer();

    public static PotionSerializer getInstance() {
        return instance;
    }

    @Override
    public String serialize(@NotNull Item toSerialize) {
        Potion potion = (Potion) toSerialize;
        return "%s;%s;%s;%s".formatted(
            potion.getId(),
            potion.getMultiplier().getId(),
            potion.getLength().getId(),
            potion.getScope().getId()
        );
    }

    @Override
    public Potion deserialize(@NotNull String toDeserialize) {
        Integer[] data = Arrays.stream(toDeserialize.split(";"))
            .map(Integer::parseInt)
            .toArray(Integer[]::new);
        return ((Potion) CollectibleHolder.getItem(data[0]))
            .set(
                Potion.Multiplier.of(data[1]),
                Potion.Length.of(data[2]),
                Potion.Scope.of(data[3])
            );
    }
}
