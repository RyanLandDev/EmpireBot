package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.executor.functional_interface.CommandPredicate;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;

import java.util.function.Consumer;
import java.util.function.Predicate;

public record ClaimCommandInfo(String name, String receiveMessage, String failMessage,
                               CommandPredicate<CommandEvent> check, Consumer<CommandEvent> claim) {

    public ClaimCommandInfo(String name, String receiveMessage,
                            CommandPredicate<CommandEvent> check, Consumer<CommandEvent> claim) {
        this(name, receiveMessage, "", check, claim);
    }

    public static ClaimCommandInfo collectible(String name, String failMessage,
                                               CommandPredicate<CommandEvent> check) {
        Collectible collectible = CollectibleHolder.get(name);
        return new ClaimCommandInfo(name, "Received " + collectible.format(), failMessage, check,
                event -> collectible.receive(event.getProfile()));
    }

    public static ClaimCommandInfo collectible(String name,
                                               CommandPredicate<CommandEvent> check) {
        return collectible(name, "", check);
    }

}
