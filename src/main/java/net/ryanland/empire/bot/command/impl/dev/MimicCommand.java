package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.EndlessStringArgument;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class MimicCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("mimic")
                .description("Lets the bot send a custom message.")
                .permission(Permission.DEVELOPER)
                .category(Category.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new EndlessStringArgument()
                    .id("message")
                    .description("Message to send")
        );
    }

    @Override
    public void run(CommandEvent event) {
        String message = event.getArgument("message");

        event.reply(new PresetBuilder(PresetType.SUCCESS, "Your message was sent."), true).queue();
        event.getChannel().sendMessage(message).queue();
    }
}
