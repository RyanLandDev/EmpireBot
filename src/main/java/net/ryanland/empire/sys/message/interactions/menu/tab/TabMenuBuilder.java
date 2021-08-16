package net.ryanland.empire.sys.message.interactions.menu.tab;

import net.dv8tion.jda.api.EmbedBuilder;
import net.ryanland.empire.sys.message.interactions.menu.InteractionMenuBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TabMenuBuilder implements InteractionMenuBuilder<TabMenu> {

    private final List<TabMenuPage> pages = new ArrayList<>(10);

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
        return new TabMenu(pages);
    }
}
