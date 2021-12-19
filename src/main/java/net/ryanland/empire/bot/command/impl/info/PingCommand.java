package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;

@CommandBuilder(
    name = "ping",
    description = "Gets the current bot ping."
)
public class PingCommand extends InformationCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) {
        event.reply(new PresetBuilder(
                "Ping: " + event.getJDA().getRestPing().complete() + "ms."));
    }
}
