package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.Command;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.CommandArgument;
import net.ryanland.colossus.command.executor.DisabledCommandHandler;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;

;

@CommandBuilder(
    name = "enable",
    description = "Re-enables a globally disabled command."
    //TODO no disable
)
public class EnableCommand extends DeveloperCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new CommandArgument()
                .id("command")
                .description("Command to enable")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Command command = event.getArgument("command");
        DisabledCommandHandler.getInstance().enable(command);
        event.reply(new PresetBuilder(DefaultPresetType.SUCCESS,
            "Re-enabled the `" + command.getName() + "` command."));
    }
}
