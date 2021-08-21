package net.ryanland.empire.sys.message.interactions;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.ryanland.empire.bot.command.executor.functional_interface.CommandBiConsumer;

import java.util.function.Consumer;
import java.util.function.Function;

public record ButtonClickContainer(
        CommandBiConsumer<ButtonClickEvent, Object> onClick, Function<ButtonClickEvent, Object> value) {

    public ButtonClickContainer(Consumer<ButtonClickEvent> onClick) {
        this((event, obj) -> onClick.accept(event), event -> null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(ButtonClickEvent event) {
        return (T) value.apply(event);
    }

}
