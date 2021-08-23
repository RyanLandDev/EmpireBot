package net.ryanland.empire.bot.command.executor.finalizers;

import net.ryanland.empire.bot.command.executor.cooldown.CooldownHandler;
import net.ryanland.empire.bot.events.CommandEvent;

public class CooldownFinalizer extends CommandFinalizer {

    @Override
    public void finalize(CommandEvent event) {
        if (event.getCommand().hasCooldown()) {
            CooldownHandler.newCooldown(event);
        }
    }
}
