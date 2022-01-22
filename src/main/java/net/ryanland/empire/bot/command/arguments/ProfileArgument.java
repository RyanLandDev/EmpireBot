package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.SingleArgument;
import net.ryanland.colossus.events.MessageCommandEvent;
import net.ryanland.colossus.events.SlashEvent;
import net.ryanland.empire.bot.command.inhibitor.RequiresProfileInhibitor;
import net.ryanland.empire.sys.file.database.Profile;

public class ProfileArgument extends SingleArgument<Profile> {

    public Profile resolve(User user) throws ArgumentException {
        if (!RequiresProfileInhibitor.hasProfile(user))
            throw new MalformedArgumentException("The provided user does not have a profile!");
        return Profile.of(user);
    }

    @Override
    public Profile resolveSlashCommandArgument(OptionMapping arg, SlashEvent event) throws ArgumentException {
        return resolve(arg.getAsUser());
    }

    @Override
    public Profile resolveMessageCommandArgument(String arg, MessageCommandEvent event) throws ArgumentException {
        return resolve(Colossus.getJda().retrieveUserById(arg.replaceAll("\\D", "")).complete());
    }

    @Override
    public OptionType getSlashCommandOptionType() {
        return OptionType.USER;
    }
}
