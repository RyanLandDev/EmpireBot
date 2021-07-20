package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.Argument;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.IntegerArgument;
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
    public String getDescription() {
        return "Gets the bot ping.";
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(

        );
    }

    @Override
    public void run(CommandEvent event) {
        getArguments();
        event.reply(
                new PresetBuilder(
                        "Ping: " + event.getJDA().getRestPing().complete() + "ms."
                )
        );
    }
}
