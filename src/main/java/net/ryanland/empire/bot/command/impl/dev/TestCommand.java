package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.builders.Preset;

@CommandBuilder(
    name = "test",
    description = "Developer command used for testing stuff."
)
public class TestCommand extends DeveloperCommand implements CombinedCommand {

    /**
     * Put testing code here.
     * @param event The associated {@link CommandEvent}.
     */
    private void performTest(CommandEvent event) {

    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(

        );
    }

    @Override
    public void execute(CommandEvent event) {
        performTest(event);
        event.reply(new PresetBuilder(Preset.SUCCESS, "", "Test finished."));
    }
}
