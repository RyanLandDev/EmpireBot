package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.StringArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class MimicCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("mimic")
                .description("Lets the bot send a custom message.")
                .permission(Permission.DEVELOPER)
                .category(Category.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new StringArgument()
                    .id("message")
                    .description("Message to send")
        );
    }

    @Override
    public void run(CommandEvent event) {
        String message = event.getArgument("message");

        event.performReply(new PresetBuilder(PresetType.SUCCESS, "Your message was sent."), true).queue();
        event.getChannel().sendMessage(message).queue();
    }
}
