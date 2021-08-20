package net.ryanland.empire.sys.message.interactions.menu.action;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.sys.message.interactions.ButtonClickContainer;

import java.util.function.Consumer;

public record ActionButton(Button button, ButtonClickContainer onClick) {

}
