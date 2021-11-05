package net.ryanland.empire.sys.message.interactions.menu;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.Interaction;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.events.CommandEvent;

public interface InteractionMenu {

    void send(Message message) throws CommandException;

    void send(Interaction interaction) throws CommandException;

    default void send(CommandEvent event) throws CommandException {
        throw new IllegalArgumentException("migrate to colossus");
    }
}
