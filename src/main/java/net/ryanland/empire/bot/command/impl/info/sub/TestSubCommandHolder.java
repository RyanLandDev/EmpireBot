package net.ryanland.empire.bot.command.impl.info.sub;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.IntegerArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.QuoteStringArgument;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.impl.SubCommandHolder;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

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
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new QuoteStringArgument()
                    .name("quote")
                    .id("quote"),
                new IntegerArgument()
                    .name("number")
                    .id("int")
                    .optional()
        );
    }

    @Override
    public SubCommand[] getSubCommands() {
        return new SubCommand[]{
                new EditSubCommandTest()
        };
    }
}
