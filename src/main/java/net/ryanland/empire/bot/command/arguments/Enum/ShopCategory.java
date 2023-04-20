package net.ryanland.empire.bot.command.arguments.Enum;

import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.interactions.menu.tab.TabMenuBuilder;
import net.ryanland.colossus.sys.interactions.menu.tab.TabMenuPage;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.message.Emojis;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ShopCategory implements EnumArgument.InputEnum, Emojis {

    BUILDINGS("buildings", "🏘") {
        @Override
        public TabMenuBuilder getTabMenuBuilder(CommandEvent event) {
            TabMenuBuilder builder = new TabMenuBuilder();

            for (BuildingType type :
                Arrays.stream(BuildingType.values())
                    .filter(building -> !building.isBaseType())
                    .collect(Collectors.toList())
            ) {
                builder.addPages(new TabMenuPage(type.getFullName(),
                    type.getEmbed(Profile.of(event)), type.getEmoji(), false
                ));
            }

            return builder;
        }
    },
    PERKS("Perks", "✨") {
        @Override
        public TabMenuBuilder getTabMenuBuilder(CommandEvent event) {
            TabMenuBuilder builder = new TabMenuBuilder();

            //for ()//TODO

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
