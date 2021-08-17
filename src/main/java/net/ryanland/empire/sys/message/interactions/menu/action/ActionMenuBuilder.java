package net.ryanland.empire.sys.message.interactions.menu.action;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.menu.InteractionMenuBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ActionMenuBuilder implements InteractionMenuBuilder<ActionMenu> {

    private final List<ActionButton> actionButtons = new ArrayList<>(25);
    private PresetBuilder embed;

    public ActionMenuBuilder setEmbed(PresetBuilder embed) {
        this.embed = embed;
        return this;
    }

    public ActionMenuBuilder addButtons(ActionButton... buttons) {
        this.actionButtons.addAll(Arrays.asList(buttons));
        return this;
    }

    public ActionMenuBuilder addButtons(List<ActionButton> buttons) {
        return addButtons(buttons.toArray(new ActionButton[0]));
    }

    public ActionMenuBuilder addButton(ActionButton button) {
        actionButtons.add(button);
        return this;
    }

    public ActionMenuBuilder insertButton(int index, ActionButton button) {
        actionButtons.add(index, button);
        return this;
    }

    public ActionMenuBuilder addButton(Button button, Consumer<ButtonClickEvent> onClick) {
        return addButton(new ActionButton(button, onClick));
    }

    public ActionMenuBuilder insertButton(int index, Button button, Consumer<ButtonClickEvent> onClick) {
        return insertButton(index, new ActionButton(button, onClick));
    }

    @Override
    public ActionMenu build() {
        return new ActionMenu(actionButtons, embed);
    }
}
