package net.ryanland.empire.sys.interactions.tabmenu;

import net.dv8tion.jda.api.EmbedBuilder;

public class TabMenuPage {

    private final String name;
    private final EmbedBuilder embed;
    private final String emoji;
    private final boolean hidden;

    public TabMenuPage(String name, EmbedBuilder embed) {
        this(name, embed, null);
    }

    public TabMenuPage(String name, EmbedBuilder embed, String emoji) {
        this(name, embed, emoji, false);
    }

    public TabMenuPage(String name, EmbedBuilder embed, boolean hidden) {
        this(name, embed, null, hidden);
    }

    public TabMenuPage(String name, EmbedBuilder embed, String emoji, boolean hidden) {
        this.name = name;
        this.embed = embed;
        this.emoji = emoji;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public EmbedBuilder getEmbed() {
        return embed;
    }

    public String getEmoji() {
        return emoji;
    }

    public boolean isHidden() {
        return hidden;
    }
}
