package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;

public class PingCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
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
        event.performReply(
            new PresetBuilder(
                "Ping: " + event.getJDA().getRestPing().complete() + "ms."
            )
        ).queue();
    }
}
