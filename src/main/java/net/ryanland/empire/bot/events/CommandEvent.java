package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.ryanland.empire.bot.command.arguments.parsing.ParsedArgumentMap;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.GlobalDocument;
import net.ryanland.empire.sys.database.documents.impl.GuildDocument;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandEvent {

    private Command command;
    private ParsedArgumentMap parsedArgs;
    private final SlashCommandEvent event;

    public CommandEvent(SlashCommandEvent event) {
        this.event = event;
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

    private ReplyAction sendReply(Message message, boolean ephemeral) {
        return event.reply(message).setEphemeral(ephemeral);
    }

    private ReplyAction sendReply(MessageEmbed embed, boolean ephemeral) {
        return event.replyEmbeds(embed).setEphemeral(ephemeral);
    }

    private ReplyAction sendReply(String message, boolean ephemeral) {
        return event.reply(message).setEphemeral(ephemeral);
    }

    public ReplyAction reply(Message message) {
        return sendReply(message, false);
    }

    public ReplyAction reply(Message message, boolean ephemeral) {
        return sendReply(message, ephemeral);
    }

    public ReplyAction reply(String message) {
        return sendReply(message, false);
    }

    public ReplyAction reply(String message, boolean ephemeral) {
        return sendReply(message, ephemeral);
    }

    public ReplyAction reply(MessageEmbed embed) {
        return sendReply(embed, false);
    }

    public ReplyAction reply(MessageEmbed embed, boolean ephemeral) {
        return sendReply(embed, ephemeral);
    }

    public ReplyAction reply(EmbedBuilder embed) {
        return sendReply(embed.build(), false);
    }

    public ReplyAction reply(EmbedBuilder embed, boolean ephemeral) {
        return sendReply(embed.build(), ephemeral);
    }

    public ReplyAction reply(PresetBuilder embed) {
        return sendReply(embed.build(), false);
    }

    public ReplyAction reply(PresetBuilder embed, boolean ephemeral) {
        return sendReply(embed.build(), ephemeral);
    }

    public String[] getRawArgs() {
        return getRawArgsAsList().toArray(String[]::new);
    }

    public List<String> getRawArgsAsList() {
        List<String> args = new ArrayList<>();
        for (OptionMapping option : event.getOptions()) {
            args.add(option.getAsString());
        }
        return args;
    }

    public User getUser() {
        return event.getUser();
    }

    public UserDocument getUserDocument() {
        return DocumentCache.get(getUser(), UserDocument.class);
    }

    public GuildDocument getGuildDocument() {
        return DocumentCache.get(getUser(), GuildDocument.class);
    }

    public GlobalDocument getGlobalDocument() {
        return DocumentCache.getGlobal();
    }

    public List<OptionMapping> getOptions() {
        return event.getOptions();
    }

    public OptionMapping getOption(@NotNull String name) {
        return event.getOption(name);
    }

    public boolean isAcknowledged() {
        return event.isAcknowledged();
    }

    public InteractionHook getHook() {
        return event.getHook();
    }

    public String getName() {
        return event.getName();
    }

    public String getSubCommandName() {
        return event.getSubcommandName();
    }

    public String getSubCommandGroup() {
        return event.getSubcommandGroup();
    }

    public ReplyAction deferReply() {
        return event.deferReply();
    }

    public ReplyAction deferReply(boolean ephemeral) {
        return event.deferReply(ephemeral);
    }

    public boolean isFromGuild() {
        return event.isFromGuild();
    }

    public JDA getJDA() {
        return event.getJDA();
    }

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public String getCommandId() {
        return event.getCommandId();
    }

    public long getCommandIdLong() {
        return event.getCommandIdLong();
    }

    public ChannelType getChannelType() {
        return event.getChannelType();
    }

    public GuildChannel getGuildChannel() {
        return event.getGuildChannel();
    }

    public String getCommandPath() {
        return event.getCommandPath();
    }

    public String getId() {
        return event.getId();
    }

    public long getIdLong() {
        return event.getIdLong();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public Interaction getInteraction() {
        return event.getInteraction();
    }

    public Member getMember() {
        return event.getMember();
    }

    public OffsetDateTime getTimeCreated() {
        return event.getTimeCreated();
    }


}
