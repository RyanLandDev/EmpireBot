package net.ryanland.empire.sys.message.interactions;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ComponentLayout;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.sys.util.ExecutorUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ButtonHandler {

    private static final HashMap<Long, ButtonListener> BUTTON_LISTENERS = new HashMap<>();

    public static void addListener(InteractionHook hook, Long userId, Consumer<ButtonClickEvent> consumer) {
        BUTTON_LISTENERS.put(hook.retrieveOriginal().complete().getIdLong(), new ButtonListener(userId, consumer));
        ExecutorUtil.schedule(
                () -> hook.editOriginal("").setActionRows(Collections.emptyList()).queue(),
                2, TimeUnit.MINUTES);
    }

    public static void handleEvent(ButtonClickEvent event) {
        ButtonListener listener = BUTTON_LISTENERS.get(event.getMessageIdLong());

        if (listener != null) {
            if (event.getUser().getIdLong() != listener.userId()) {

                event.deferReply(true).addEmbeds(
                        new PresetBuilder(PresetType.ERROR, "You can't use this button.").build()
                ).queue();

                return;
            }

            listener.consumer().accept(event);
        }
    }

    private record ButtonListener(long userId, Consumer<ButtonClickEvent> consumer) {

    }

}
