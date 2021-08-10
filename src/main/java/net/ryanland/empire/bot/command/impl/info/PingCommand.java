package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class PingCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("ping")
                .description("Gets the current bot ping.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        event.reply(
                new PresetBuilder(
                        "Ping: " + event.getJDA().getRestPing().complete() + "ms."
                )
        ).queue();
    }
}
