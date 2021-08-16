package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;

public class ProfileArgument extends SingleArgument<Profile> {

    @Override
    public OptionType getType() {
        return OptionType.USER;
    }

    @Override
    public Profile parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        return new Profile(argument.getAsUser());
    }
}
