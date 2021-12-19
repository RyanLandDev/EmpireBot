package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.builders.Preset;

@CommandBuilder(
    name = "stop",
    description = "Stops the bot."
)//TODO cant be disabled
public class StopCommand extends DeveloperCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) {
        event.reply(new PresetBuilder(Preset.NOTIFICATION, "Bot shutting down...", "Shutdown"));
        System.exit(0);
    }
}
