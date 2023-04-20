package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.BaseCommand;
import net.ryanland.colossus.command.permission.PermissionHolder;
import net.ryanland.empire.bot.command.permission.DeveloperRequirement;

public abstract class DeveloperCommand extends BaseCommand {

    @Override
    public PermissionHolder getPermission() {
        return new PermissionHolder(new DeveloperRequirement());
    }
}
