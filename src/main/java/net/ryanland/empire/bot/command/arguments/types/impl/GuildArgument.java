package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuildArgument extends SingleArgument<Guild> {

    @Override
    public Guild parsed(String argument) throws ArgumentException {
        try {
            Guild guild = Empire.getJda().getGuildById(argument);
            if (guild != null) return guild;
        } catch (NumberFormatException ignored) {
        }
        throw new MalformedArgumentException("Invalid guild provided. Must be a valid ID.");
    }
}
