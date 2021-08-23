package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.events.CommandEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public record ClaimCommandInfo(String name, String receiveMessage, String failMessage,
                               Predicate<CommandEvent> check, Consumer<CommandEvent> claim) {

    public ClaimCommandInfo(String name, String receiveMessage,
                            Predicate<CommandEvent> check, Consumer<CommandEvent> claim) {
        this(name, receiveMessage, "", check, claim);
    }

}
