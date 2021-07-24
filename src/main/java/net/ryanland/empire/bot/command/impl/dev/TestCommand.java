package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class TestCommand extends Command {
    @Override
    public CommandData getData() {
        return new CommandData()
                .name("test")
                .description("Developer command used for testing stuff.")
                .permission(Permission.DEVELOPER)
                .category(Category.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {

        event.getGuildDocument()
                .setPrefix("ee")
                .update();



        event.reply(new PresetBuilder(PresetType.SUCCESS).setTitle("Test finished."));
    }
}