package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permission.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

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
        //TODO
    }
}
