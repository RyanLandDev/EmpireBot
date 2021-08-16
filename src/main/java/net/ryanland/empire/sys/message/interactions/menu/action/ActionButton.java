package net.ryanland.empire.sys.message.interactions.menu.action;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.function.Consumer;

public record ActionButton(Button button, Consumer<ButtonClickEvent> onClick) {

}
