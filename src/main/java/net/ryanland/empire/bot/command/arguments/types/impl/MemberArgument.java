package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberArgument extends SingleArgument<Member> {

    private final Pattern pattern = Pattern.compile("<@!?(\\d+)>");

    public MemberArgument() { this.type = OptionType.USER; }

    @Override
    public Member parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        return argument.getAsMember();
    }
}
