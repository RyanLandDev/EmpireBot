package net.ryanland.empire.sys.message.interactions;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.*;
import java.util.stream.Collectors;

public class InteractionUtil {

    public static List<ActionRow> of(List<Button> components) {
        Deque<Button> buttons = new ArrayDeque<>(components);
        List<Button> queue = new ArrayList<>();
        List<ActionRow> rows = new ArrayList<>();

        while (!buttons.isEmpty()) {
            queue.add(buttons.pop());
            if (queue.size() >= 5) {
                rows.add(ActionRow.of(queue.stream().filter(Objects::nonNull).collect(Collectors.toList())));
                queue.clear();
            }
        }

        if (!queue.isEmpty()) {
            rows.add(ActionRow.of(queue.stream().filter(Objects::nonNull).collect(Collectors.toList())));
        }

        return rows;
    }
}
