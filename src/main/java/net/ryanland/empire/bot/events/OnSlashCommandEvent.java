package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.impl.Command;
import org.jetbrains.annotations.NotNull;

public class OnSlashCommandEvent extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        CommandHandler.run(new CommandEvent(event));
    }
}
