package net.ryanland.empire.bot.command.executor.checks;

import net.ryanland.empire.bot.command.executor.checks.impl.CooldownCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.DisabledCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.PermissionCheck;
import net.ryanland.empire.bot.command.executor.checks.impl.RequiresProfileCheck;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public abstract class CommandCheck {

    @SuppressWarnings("all")
    private final static CommandCheck[] CHECKS = new CommandCheck[]{
            new DisabledCheck(),
            new PermissionCheck(),
            new RequiresProfileCheck(),
            new CooldownCheck()
    };

    public static CommandCheck[] getChecks() {
        return CHECKS;
    }

    public abstract boolean check(CommandEvent event);

    public abstract PresetBuilder buildMessage(CommandEvent event);
}
