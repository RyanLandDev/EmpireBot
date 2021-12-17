package net.ryanland.empire.bot.command.permission;

import net.dv8tion.jda.api.entities.Member;
import net.ryanland.colossus.command.permissions.BotOwnerRequirement;
import net.ryanland.colossus.command.permissions.PermissionRequirement;

public class DeveloperRequirement implements PermissionRequirement {

    @Override
    public boolean check(Member member) {
        return new BotOwnerRequirement().check(member);
        //TODO
    }

    @Override
    public String getName() {
        return "Developer";
    }
}
