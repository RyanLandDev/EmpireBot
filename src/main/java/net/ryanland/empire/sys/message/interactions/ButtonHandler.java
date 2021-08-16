package net.ryanland.empire.sys.message.interactions;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class ButtonHandler {

    private static final HashMap<Long, ButtonListener> BUTTON_LISTENERS = new HashMap<>();

    public static void addListener(InteractionHook message, Long userId, Consumer<ButtonClickEvent> consumer) {
        try {
            BUTTON_LISTENERS.put(message.retrieveOriginal().submit().get().getIdLong(), new ButtonListener(userId, consumer));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void handleEvent(ButtonClickEvent event) {
        ButtonListener listener = BUTTON_LISTENERS.get(event.getMessageIdLong());

        if (listener != null) {
            if (event.getUser().getIdLong() == listener.userId) {
                listener.consumer.accept(event);
            }
        }
    }

    private record ButtonListener(long userId, Consumer<ButtonClickEvent> consumer) {

    }
}
