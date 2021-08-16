package net.ryanland.empire.sys.message.interactions.tabmenu;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Comparator;

public class TabMenuBuilder {

    private final ArrayList<TabMenuPage> pages = new ArrayList<>(10);
    private long channelId;
    private long userId;

    public TabMenuBuilder setChannelId(long channelId) {
        this.channelId = channelId;
        return this;
    }

    public TabMenuBuilder setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public TabMenuBuilder addPage(TabMenuPage page) {
        pages.add(page);
        return this;
    }

    public TabMenuBuilder addPage(String name, EmbedBuilder embed, String emoji, boolean hidden) {
        return addPage(new TabMenuPage(name, embed, emoji, hidden));
    }

    public TabMenuBuilder addPage(String name, EmbedBuilder embed, boolean hidden) {
        return addPage(name, embed, null, hidden);
    }

    public TabMenuBuilder addPage(String name, EmbedBuilder embed, String emoji) {
        return addPage(name, embed, emoji, false);
    }

    public TabMenuBuilder addPage(String name, EmbedBuilder embed) {
        return addPage(name, embed, null);
    }

    public TabMenuBuilder orderPages() {
        pages.sort(Comparator.comparing(TabMenuPage::getName));
        return this;
    }

    public TabMenu build() {
        return new TabMenu(pages, userId);
    }
}
