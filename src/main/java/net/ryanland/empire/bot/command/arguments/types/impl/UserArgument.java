package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserArgument extends SingleArgument<User> {

    @Override
    public OptionType getType() {
        return OptionType.USER;
    }

    @Override
    public User parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        return argument.getAsUser();
    }
}
