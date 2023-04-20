package net.ryanland.empire.bot.command.permission;

import net.ryanland.colossus.command.permission.PermissionRequirement;
import net.ryanland.colossus.command.permission.impl.BotOwnerRequirement;
import net.ryanland.colossus.events.command.BasicCommandEvent;

public class DeveloperRequirement implements PermissionRequirement {

    @Override
    public boolean check(BasicCommandEvent member) {
        return new BotOwnerRequirement().check(member);
        //TODO
    }

    @Override
    public String getName() {
        return "Developer";
    }
}
