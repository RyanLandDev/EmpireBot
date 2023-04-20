package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.primitive.StringArgument;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.command.regular.MessageCommand;
import net.ryanland.colossus.command.regular.SlashCommand;
import net.ryanland.colossus.events.command.MessageCommandEvent;
import net.ryanland.colossus.events.command.SlashCommandEvent;
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
                .name("message")
                .description("Message to send")
        );
    }

    @Override
    public void run(MessageCommandEvent event) throws CommandException {
        String message = event.getArgument("message");
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void run(SlashCommandEvent event) throws CommandException {
        String message = event.getArgument("message");
        event.reply(new PresetBuilder(Preset.SUCCESS, "Your message was sent.").setEphemeral(true));
        event.getChannel().sendMessage(message).queue();
    }
}
