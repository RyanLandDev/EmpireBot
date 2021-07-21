package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.IntegerArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.QuoteStringArgument;
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
    public void run(CommandEvent event) {
        event.reply(
                new PresetBuilder(
                        "Ping: " + event.getJDA().getRestPing().complete() + "ms. Quote is "
                                +event.getArgument("quote")+", number is "+event.getArgument("int")
                )
        );
    }
}
