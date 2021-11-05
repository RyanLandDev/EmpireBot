package net.ryanland.empire.sys.message.interactions.menu;

import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.sys.message.interactions.ButtonClickContainer;

public record ActionButton(Button button, ButtonClickContainer onClick) {

}
