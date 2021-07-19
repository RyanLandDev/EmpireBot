package net.ryanland.empire.bot.command.executor.checks;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public interface CommandCheck {

    boolean check(CommandEvent event);

    PresetBuilder buildMessage(CommandEvent event);
}
