package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.CommandArgument;
import net.ryanland.empire.bot.command.executor.flags.Flag;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class EnableCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("enables")
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
        );
    }

    @Override
    public void run(CommandEvent event) {
        Command command = event.getArgument("command");
        Empire.getDisabledCommandHandler().enable(command);

        event.reply(
                new PresetBuilder(PresetType.SUCCESS,
                        "Re-enabled the `"+command.getName()+"` command.")
        );
    }
}