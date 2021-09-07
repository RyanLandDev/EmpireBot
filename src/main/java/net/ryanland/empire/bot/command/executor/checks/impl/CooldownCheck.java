package net.ryanland.empire.bot.command.executor.checks.impl;

import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.command.executor.cooldown.CooldownHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.util.DateUtil;

import java.util.Date;

public class CooldownCheck extends CommandCheck {

    @Override
    public boolean check(CommandEvent event) {
        return event.getCommand().hasCooldown() && CooldownHandler.isCooldownActive(event);
    }

    @Override
    public PresetBuilder buildMessage(CommandEvent event) {
        return new PresetBuilder(
                PresetType.ERROR,
                "This command is currently on cooldown.\nTime left: " +
                        DateUtil.formatRelative(new Date(
                                CooldownHandler.getActiveCooldown(event).expires().getTime() - System.currentTimeMillis())),
                "On Cooldown"
        );
    }
}
