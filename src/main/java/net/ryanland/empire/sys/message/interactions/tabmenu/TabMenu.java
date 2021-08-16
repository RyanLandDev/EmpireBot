package net.ryanland.empire.sys.message.interactions.tabmenu;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.interactions.ButtonHandler;
import net.ryanland.empire.sys.message.interactions.InteractionUtil;

import java.util.*;
import java.util.function.Consumer;

public class TabMenu {

    private final TabMenuPage[] pages;
    private final long userId;

    public TabMenu(List<TabMenuPage> pages, long userId) {
        this(pages.toArray(new TabMenuPage[0]), userId);
    }

    public TabMenu(TabMenuPage[] pages, long userId) {
        this.pages = pages;
        this.userId = userId;
    }

    public void send(CommandEvent commandEvent) {
        // Set all embed titles to match the category name
        for (TabMenuPage page : pages) {
            EmbedBuilder embed = page.getEmbed();
            embed.setTitle(page.getName());
        }

        // Init vars
        HashMap<String, TabMenuPage> pageMap = new HashMap<>(pages.length);
        List<Button> buttons = new ArrayList<>();

        // Iterate over all pages
        for (TabMenuPage page : pages) {
            // Skip if this page should be hidden
            if (page.isHidden()) {
                continue;
            }

            // Create the Button
            Button button = Button.secondary("BUTTON:" + page.getName(), page.getName());
            if (page.getEmoji() != null) {
                button = button.withEmoji(Emoji.fromUnicode(page.getEmoji()));
            }

            // Put the page and buttons in their respective data structures
            pageMap.put(button.getId(), page);
            buttons.add(button);
        }

        // Send the message and set the action rows
        InteractionHook hook = commandEvent.performReply(pages[0].getEmbed().build())
                .addActionRows(InteractionUtil.of(buttons)).complete();

        // Add a listener for when a button is clicked
        Consumer<ButtonClickEvent> consumer = event -> {
            event.deferEdit().queue();
            hook.editOriginalEmbeds(pageMap.get(event.getComponentId()).getEmbed().build())
                        .queue();
            };
        ButtonHandler.addListener(hook, userId, consumer);
    }

}
