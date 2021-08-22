package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.menu.tab.TabMenuBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ShopCategory implements EnumArgument.InputEnum, Emojis {

    BUILDINGS("buildings", "🏘"){
        @Override
        public TabMenuBuilder getTabMenuBuilder(CommandEvent event) {
            TabMenuBuilder builder = new TabMenuBuilder();

            for (BuildingType type :
                    Arrays.stream(BuildingType.values())
                    .filter(building -> !building.isBaseType())
                    .collect(Collectors.toList())
            ) {
                builder.addPage(
                        type.getFullName(),
                        type.getEmbed(event.getProfile()), type.getEmoji()
                );
            }

            return builder;
        }
    }
    ;

    private final String title;
    private final String emoji;
    private final boolean hidden;

    ShopCategory(String title, String emoji) {
        this(title, false, emoji);
    }

    ShopCategory(String title, boolean hidden, String emoji) {
        this.title = title;
        this.hidden = hidden;
        this.emoji = emoji;
    }

    public abstract TabMenuBuilder getTabMenuBuilder(CommandEvent event);

    @Override
    public String getTitle() {
        return title;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }
}
