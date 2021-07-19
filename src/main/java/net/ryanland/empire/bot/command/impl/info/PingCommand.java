package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class PingCommand extends Command {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public void run(CommandEvent event) {
        event.reply(
                new PresetBuilder(
                        "Ping: " + event.getJDA().getRestPing().complete() + "ms."
                )
        );
    }
}
