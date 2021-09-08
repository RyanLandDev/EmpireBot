package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class TestCommand extends Command {

    /**
     * Put testing code here.
     *
     * @param event The associated {@link CommandEvent}.
     */
    private void performTest(CommandEvent event) {

    }

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("test")
            .description("Developer command used for testing stuff.")
            .permission(Permission.DEVELOPER)
            .category(Category.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(

        );
    }

    @Override
    public void run(CommandEvent event) {
        performTest(event);
        event.reply(new PresetBuilder(PresetType.SUCCESS, "", "Test finished."));
    }
}
