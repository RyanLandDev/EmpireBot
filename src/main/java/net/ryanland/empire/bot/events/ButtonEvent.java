package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ryanland.empire.sys.message.interactions.ButtonHandler;
import org.jetbrains.annotations.NotNull;

public class ButtonEvent extends ListenerAdapter {

    public void onButtonClick(@NotNull ButtonClickEvent event) {
        ButtonHandler.handleEvent(event);
    }

}
