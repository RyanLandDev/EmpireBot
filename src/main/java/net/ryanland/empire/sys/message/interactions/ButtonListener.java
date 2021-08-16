package net.ryanland.empire.sys.message.interactions;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.util.function.Consumer;

public record ButtonListener(long userId, Consumer<ButtonClickEvent> consumer) {

}
