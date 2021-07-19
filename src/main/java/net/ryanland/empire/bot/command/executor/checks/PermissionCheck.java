package net.ryanland.empire.bot.command.executor.checks;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class PermissionCheck implements CommandCheck {

    @Override
    public boolean check(CommandEvent event) {
        return event.getCommand().getPermission().hasPermission(event.getMember());
    }

    @Override
    public PresetBuilder buildMessage(CommandEvent event) {
        return new PresetBuilder(
                PresetType.ERROR, "You do not have permission to execute this command.", "Insufficient Permissions"
        );
    }
}
