package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class CommandEvent extends GuildMessageReceivedEvent {
    public CommandEvent(Message message) {
        super(message.getJDA(), 0, message);
    }

    private void sendReply(Message message) {
        getChannel().sendMessage(message).reference(getMessage()).mentionRepliedUser(false).queue();
    }

    private void sendReply(MessageEmbed embed) {
        getChannel().sendMessage(embed).reference(getMessage()).mentionRepliedUser(false).queue();
    }

    private void sendReply(String message) {
        getChannel().sendMessage(message).reference(getMessage()).mentionRepliedUser(false).queue();
    }

    public void reply(Message message) {
        sendReply(message);
    }

    public void reply(String message) {
        sendReply(message);
    }

    public void reply(MessageEmbed embed) {
        sendReply(embed);
    }

    public void reply(EmbedBuilder embed) {
        sendReply(embed.build());
    }

    public void reply(PresetBuilder embed) {
        sendReply(embed.build());
    }
}
