package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.Category;
import net.ryanland.colossus.command.DefaultCommand;
import net.ryanland.colossus.command.permissions.PermissionBuilder;
import net.ryanland.colossus.command.permissions.PermissionHolder;
import net.ryanland.empire.bot.command.permission.DeveloperRequirement;

public abstract class DeveloperCommand extends DefaultCommand {

    @Override
    public PermissionHolder getPermission() {
        return new PermissionBuilder()
            .addRequirement(new DeveloperRequirement())
            .build();
    }

    @Override
    public Category getCategory() {
        return new Category("Developer", "Utility commands for bot developers only.", "💻");
    }
}
