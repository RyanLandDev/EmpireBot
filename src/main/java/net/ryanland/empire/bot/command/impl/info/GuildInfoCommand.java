package net.ryanland.empire.bot.command.impl.info;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.IMentionable;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.List;

public class GuildInfoCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("guildinfo")
                .description("Information about the guild you're currently in.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        PresetBuilder builder = new PresetBuilder();
        final Guild GUILD = event.getGuild();
        // Returns all the roles in the Discord as a string of mentions.
        List<String> mentionableRoles = GUILD.getRoles().stream()
                .map(IMentionable::getAsMention)
                .collect(java.util.stream.Collectors.toList());

        builder
                .setTitle(GUILD.getName())
                .setDescription(GUILD.getDescription())
                .setThumbnail(GUILD.getIconUrl())
                .addField("Date Created", String.format("<t:%s>",GUILD.getTimeCreated().toEpochSecond()), true)
                .addField("Member Count", String.format("`%s`",GUILD.getMemberCount()), true)
                //.addField("Owner", String.format("<@%s>", Objects.requireNonNull(GUILD.getOwner()).getIdLong()), false)
                .addField("Boosts", String.format("`%s`", GUILD.getBoostCount()), true)
                .addField("Boost Level", String.format("`%s`", GUILD.getBoostTier().toString().toLowerCase()), true)
                .addField("","", false)
                .addField("Emoji Count", String.format("`%s`", GUILD.getEmotes().size()), true)
                .addField("Sticker Count", "`-`", true)
                .addField("Roles", String.join(" ", mentionableRoles))
                .setImage(GUILD.getSplashUrl());

        event.reply(builder);
    }
    }
