package net.ryanland.empire.bot.command.executor.finalizers;

import net.ryanland.empire.bot.command.executor.CooldownHandler;
import net.ryanland.empire.bot.events.CommandEvent;

public class CooldownFinalizer implements CommandFinalizer {

    @Override
    public void finalize(CommandEvent event) {
        CooldownHandler.newCooldown(event);
    }
}
