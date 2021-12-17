package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.executor.data.DisabledCommandHandler;
import net.ryanland.empire.bot.command.executor.data.Flag;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.sys.message.builders.PresetType;

public class EnableCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("enable")
            .description("Re-enables a globally disabled command.")
            .category(Category.DEVELOPER)
            .permission(Permission.DEVELOPER)
            .flags(Flag.NO_DISABLE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new CommandArgument()
                .id("command")
                .description("Command to enable")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Command command = event.getArgument("command");
        DisabledCommandHandler.getInstance().enable(command);

        event.performReply(
            new PresetBuilder(PresetType.SUCCESS,
                "Re-enabled the `" + command.getName() + "` command.")
        ).queue();
    }
}
