package net.ryanland.empire.sys.interactions;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.ryanland.empire.Empire;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ButtonHandler {

    private static HashMap<Long, ButtonListener> BUTTON_LISTENERS = new HashMap<>();

    public static void addListener(Message message, Long userId, Consumer<ButtonClickEvent> consumer) {
        BUTTON_LISTENERS.put(message.getIdLong(), new ButtonListener(userId, consumer));
    }

    public static void handleEvent(ButtonClickEvent event) {
        ButtonListener listener = BUTTON_LISTENERS.get(event.getMessageIdLong());

        if (listener != null) {
            if (event.getUser().getIdLong() == listener.userId) {
                listener.consumer.accept(event);
            }
        }
    }

    private static class ButtonListener {

        private final long userId;
        private final Consumer<ButtonClickEvent> consumer;

        private ButtonListener(long userId, Consumer<ButtonClickEvent> consumer) {
            this.userId = userId;
            this.consumer = consumer;
        }
    }
}
