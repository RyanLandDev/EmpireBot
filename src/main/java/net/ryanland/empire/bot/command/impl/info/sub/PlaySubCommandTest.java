package net.ryanland.empire.bot.command.impl.info.sub;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.IntegerArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.StringArgument;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;

public class PlaySubCommandTest extends SubCommand {

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new IntegerArgument()
                .name("test2")
                .id("tid")
        );
    }

    @Override
    public void run(CommandEvent event) {
        Integer arg = event.getArgument("tid");
        event.reply(arg.toString());
    }
}
