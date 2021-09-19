package net.ryanland.empire.sys.file.serializer;

import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class PotionsSerializer implements Serializer<List<List>, List<Potion>> {

    private static final PotionsSerializer instance = new PotionsSerializer();

    public static PotionsSerializer getInstance() {
        return instance;
    }

    @Override
    public List<List> serialize(@NotNull List<Potion> toSerialize) {
        return toSerialize.stream()
            .map(potion -> Arrays.asList(potion.serialize(), potion.getExpires()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Potion> deserialize(@NotNull List<List> toDeserialize) {
        return toDeserialize.stream()
            .map(potion -> {
                String data = (String) potion.get(0);
                Date expires = (Date) potion.get(1);

                return PotionSerializer.getInstance().deserialize(data)
                    .setExpires(expires);
            })
            .collect(Collectors.toList());
    }
}
