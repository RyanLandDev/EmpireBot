package net.ryanland.empire.bot.command.impl.info.sub;

import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.command.permissions.Permission;

public class TestSubCommandHolder extends SubCommandHolder {

    @Override
    public String getName() {
        return "sub";
    }

    @Override
    public String getDescription() {
        return "Sub command test.";
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public SubCommand[] getSubCommands() {
        return new SubCommand[]{
                new EditSubCommandTest(),
                new PlaySubCommandTest()
        };
    }
}
