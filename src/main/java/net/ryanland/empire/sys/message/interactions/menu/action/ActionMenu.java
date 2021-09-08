package net.ryanland.empire.sys.message.interactions.menu.action;

import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.ButtonClickContainer;
import net.ryanland.empire.sys.message.interactions.ButtonHandler;
import net.ryanland.empire.sys.message.interactions.InteractionUtil;
import net.ryanland.empire.sys.message.interactions.menu.InteractionMenu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public ActionButton[] getActionButtons() {
        return actionButtons;
    }

    @Override
    public void send(Interaction interaction) {

        // Stream map of actual buttons
        List<Button> buttons = Arrays.stream(actionButtons)
            .map(ActionButton::button)
            .collect(Collectors.toList());

        // Create container map
        HashMap<String, ButtonClickContainer> buttonConsumers = new HashMap<>();
        Arrays.asList(actionButtons)
            .forEach(button -> buttonConsumers.put(
                button.button().getId(), button.onClick()
            ));

        // Add button listener
        ButtonHandler.addListener(interaction.replyEmbeds(embed.build())
                .addActionRows(InteractionUtil.of(buttons))
                .complete(),
            buttonEvent -> new ButtonHandler.ButtonListener(
                interaction.getUser().getIdLong(),
                clickEvent -> buttonConsumers.get(clickEvent.getComponentId())
            ));

    }
}
