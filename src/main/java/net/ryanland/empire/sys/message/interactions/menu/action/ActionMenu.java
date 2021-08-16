package net.ryanland.empire.sys.message.interactions.menu.action;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.ButtonHandler;
import net.ryanland.empire.sys.message.interactions.InteractionUtil;
import net.ryanland.empire.sys.message.interactions.menu.InteractionMenu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ActionMenu implements InteractionMenu {

    private final ActionButton[] actionButtons;
    private final PresetBuilder embed;

    public ActionMenu(List<ActionButton> actionButtons, PresetBuilder embed) {
        this(actionButtons.toArray(new ActionButton[0]), embed);
    }

    public ActionMenu(ActionButton[] actionButtons, PresetBuilder embed) {
        this.actionButtons = actionButtons;
        this.embed = embed;
    }

    @Override
    public void send(CommandEvent commandEvent) {

        // Stream map of actual buttons
        List<Button> buttons = Arrays.stream(actionButtons)
                .map(ActionButton::button)
                .collect(Collectors.toList());

        // Create consumer map
        HashMap<String, Consumer<ButtonClickEvent>> buttonConsumers = new HashMap<>();
        Arrays.asList(actionButtons)
                .forEach(button -> buttonConsumers.put(
                        button.button().getId(), button.onClick()
                ));

        // Add button listener
        ButtonHandler.addListener(commandEvent.performReply(embed)
                .addActionRows(InteractionUtil.of(buttons))
                .complete(), commandEvent.getUser().getIdLong(),
                buttonEvent -> {
                    buttonEvent.deferEdit().queue();
                    buttonConsumers.get(buttonEvent.getComponentId()).accept(buttonEvent);
                });
    }
}
