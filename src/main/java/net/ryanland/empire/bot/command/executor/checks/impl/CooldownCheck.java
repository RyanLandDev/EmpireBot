package net.ryanland.empire.bot.command.executor.checks.impl;

import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.data.CooldownHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class CooldownCheck extends CommandCheck {

    @Override
    public boolean check(CommandEvent event) {
        return CooldownHandler.isCooldownActive(event);
    }

    @Override
    public PresetBuilder buildMessage(CommandEvent event) {
        return new PresetBuilder(
                PresetType.ERROR, "This command is currently on cooldown.", "On Cooldown"
        );
    }
}
