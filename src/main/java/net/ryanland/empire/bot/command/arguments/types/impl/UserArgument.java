package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserArgument extends SingleArgument<User> {

    private final Pattern pattern = Pattern.compile("<@!?(\\d+)>");

    @Override
    public User parsed(String argument, CommandEvent event) throws ArgumentException {
        try {
            User user = Empire.getJda().retrieveUserById(argument).complete();
            if (user != null) return user;
        } catch (NumberFormatException ignored) {
        }

        Matcher matcher = pattern.matcher(argument);
        if (matcher.find()) {
            User user = Empire.getJda().retrieveUserById(MiscUtil.parseSnowflake(matcher.group(1))).complete();
            if (user != null) return user;
        }
        throw new MalformedArgumentException("Invalid user provided. Must be a valid mention or ID.");
    }
}
