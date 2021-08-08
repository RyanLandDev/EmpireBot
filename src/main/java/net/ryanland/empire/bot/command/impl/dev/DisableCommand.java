package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.CommandArgument;
import net.ryanland.empire.bot.command.executor.CommandException;
import net.ryanland.empire.bot.command.executor.data.DisabledCommandHandler;
import net.ryanland.empire.bot.command.executor.data.Flag;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class DisableCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("disable")
                .description("Disables a command globally.")
                .category(Category.DEVELOPER)
                .permission(Permission.DEVELOPER)
                .flags(Flag.NO_DISABLE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new CommandArgument()
                    .id("command")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Command command = event.getArgument("command");
        DisabledCommandHandler.getInstance().disable(command);

        event.reply(
                new PresetBuilder(PresetType.SUCCESS,
                        "Disabled the `"+command.getName()+"` command.")
        );
    }
}
