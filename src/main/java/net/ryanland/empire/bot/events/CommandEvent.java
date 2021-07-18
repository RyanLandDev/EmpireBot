package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CommandEvent extends GuildMessageReceivedEvent {
    public CommandEvent(Message message) {
        super(message.getJDA(), 0, message);
    }
}
