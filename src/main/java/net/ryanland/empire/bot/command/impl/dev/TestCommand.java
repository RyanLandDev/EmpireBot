package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.BooleanArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.Test;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Collections;
import java.util.EnumSet;

public class TestCommand extends Command {
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
                new EnumArgument<Test>()
                    .setEnum(Test.class)
                    .id("test")
                    .description("my description")
        );
    }

    @Override
    public void run(CommandEvent event) {
        Test test = event.getArgument("test");

        event.getChannel().sendMessage("name: " + test.getName() + "\nextra thing: " + test.getExtraThing()).queue();

        event.reply(new PresetBuilder(PresetType.SUCCESS, "", "Test finished."));
    }
}
