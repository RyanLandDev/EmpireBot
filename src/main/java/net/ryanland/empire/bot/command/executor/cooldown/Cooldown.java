package net.ryanland.empire.bot.command.executor.cooldown;

import net.ryanland.empire.bot.command.impl.Command;

import java.util.Date;

public record Cooldown(Command command, Date expires) {

}
