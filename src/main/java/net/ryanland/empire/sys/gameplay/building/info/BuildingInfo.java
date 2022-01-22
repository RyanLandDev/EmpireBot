package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.Emojis;

import java.util.ArrayList;
import java.util.List;

public class BuildingInfo extends ArrayList<BuildingInfoSegment> implements Emojis {

    public BuildingInfo(List<BuildingInfoSegment> segments) {
        addAll(segments);
    }

    public PresetBuilder build() {
        PresetBuilder embed = new PresetBuilder();

        for (BuildingInfoSegment segment : this) {
            embed.addField(HORIZONTAL_LINE, segment.build());
        }

        return embed;
    }
}
