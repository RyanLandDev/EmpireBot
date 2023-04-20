package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.builders.Preset;

@CommandBuilder(
    name = "stop",
    description = "Stops the bot.",
    canBeDisabled = false
)
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
