package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.Command;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.CommandArgument;
import net.ryanland.colossus.command.executor.DisabledCommandHandler;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.builders.Preset;

@CommandBuilder(
    name = "disable",
    description = "Disables a command globally."
)//TODO immune to disabling flag
public class DisableCommand extends DeveloperCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new CommandArgument()
                .id("command")
                .description("Command to disable")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Command command = event.getArgument("command");
        DisabledCommandHandler.getInstance().disable(command);
        event.reply(new PresetBuilder(Preset.SUCCESS,
                "Disabled the `" + command.getName() + "` command."));
    }
}
