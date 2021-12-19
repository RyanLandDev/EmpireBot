package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.MessageCommand;
import net.ryanland.colossus.command.SlashCommand;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.primitive.StringArgument;
import net.ryanland.colossus.events.MessageCommandEvent;
import net.ryanland.colossus.events.SlashEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.message.builders.Preset;

@CommandBuilder(
    name = "mimic",
    description = "Lets the bot send a custom message."
)
public class MimicCommand extends DeveloperCommand implements SlashCommand, MessageCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new StringArgument()
                .id("message")
                .description("Message to send")
        );
    }

    @Override
    public void run(MessageCommandEvent event) throws CommandException {
        String message = event.getArgument("message");
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void run(SlashEvent event) throws CommandException {
        String message = event.getArgument("message");
        event.reply(new PresetBuilder(Preset.SUCCESS, "Your message was sent."), true);
        event.getChannel().sendMessage(message).queue();
    }
}
