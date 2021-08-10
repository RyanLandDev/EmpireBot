package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.command.impl.Command;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull net.dv8tion.jda.api.events.interaction.SlashCommandEvent event) {
        String commandName = event.getName();
        Command command = CommandHandler.getCommand(commandName);

        CommandHandler.run(new CommandEvent(event));
    }
}
