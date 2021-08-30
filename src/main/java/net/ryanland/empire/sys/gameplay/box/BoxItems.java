package net.ryanland.empire.sys.gameplay.box;

import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.util.NumberUtil;
import net.ryanland.empire.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class BoxItems {

    private int maxProbability = 1;
    private final List<BoxItem> items = new ArrayList<>();

    public BoxItems add(BoxItem item) {
        items.add(item);
        if (item.probabilityUntil() > maxProbability) maxProbability = item.probabilityUntil();
        return this;
    }

    public BoxItems add(int probabilityUntil, Collectible collectible) {
        return add(new BoxItem(probabilityUntil, collectible));
    }

    public Collectible pick() {
        int index = RandomUtil.randomInt(1, maxProbability);

        int min = 1;
        for (BoxItem item : items) {
            if (NumberUtil.inRange(index, min, item.probabilityUntil)) {
                return item.collectible;
            }
            min = item.probabilityUntil;
        }

        throw new RuntimeException("Invalid items provided");
    }

    private record BoxItem(int probabilityUntil, Collectible collectible) {

    }
}
