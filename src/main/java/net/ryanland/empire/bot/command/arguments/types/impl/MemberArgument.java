package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleEventArgument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberArgument extends SingleEventArgument<Member> {

    private final Pattern pattern = Pattern.compile("<@!?(\\d+)>");

    @Override
    public Member parsed(String argument, CommandEvent event) throws ArgumentException {
        Guild guild = event.getGuild();

        try {
            Member member = guild.retrieveMemberById(argument).complete();
            if (member != null) return member;
        } catch (NumberFormatException ignored) {
        }

        Matcher matcher = pattern.matcher(argument);
        if (matcher.find()) {
            Member member = guild.retrieveMemberById(MiscUtil.parseSnowflake(matcher.group(1))).complete();
            if (member != null) return member;
        }
        throw new MalformedArgumentException("Invalid member provided. Must be a valid mention or ID.");
    }
}
