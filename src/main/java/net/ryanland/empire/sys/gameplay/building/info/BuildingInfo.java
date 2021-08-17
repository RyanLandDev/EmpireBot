package net.ryanland.empire.sys.gameplay.building.info;

import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.ArrayList;
import java.util.List;

public class BuildingInfo extends ArrayList<BuildingInfoSegment> implements Emojis {

    public BuildingInfo(List<BuildingInfoSegment> segments) {
        addAll(segments);
    }

    public PresetBuilder build() {
        PresetBuilder embed = new PresetBuilder()
                .addLogo();

        for (BuildingInfoSegment segment : this) {
            embed.addField(HORIZONTAL_LINE, segment.build());
        }

        return embed;
    }
}
