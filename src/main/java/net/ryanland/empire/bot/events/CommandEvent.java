package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.parsing.ParsedArgumentMap;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEvent extends GuildMessageReceivedEvent {

    private Command command;
    private ParsedArgumentMap parsedArgs;

    public CommandEvent(Message message) {
        super(message.getJDA(), 0, message);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ParsedArgumentMap getParsedArgs() {
        return parsedArgs;
    }

    public void setParsedArgs(ParsedArgumentMap parsedArgs) {
        this.parsedArgs = parsedArgs;
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgument(String id) {
        return (T) parsedArgs.get(id);
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

    public String getPrefix() {
        //TODO for custom server prefixes
        return Empire.getConfig().getPrefix();
    }

    public String[] getRawArgs() {
        String[] args = getMessage().getContentRaw().split("\\s+");
        args[0] = args[0].substring(getPrefix().length());
        return args;
    }

    public List<String> getRawArgsAsList() {
        return new ArrayList<>(Arrays.asList(getRawArgs()));
    }
}
