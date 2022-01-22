package net.ryanland.empire.bot.command.inhibitor;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.command.inhibitors.Inhibitor;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.RequiresProfile;

public class RequiresProfileInhibitor implements Inhibitor {

    @Override
    public boolean check(CommandEvent event) {
        return event.getCommand() instanceof RequiresProfile && hasProfile(event.getUser());
    }

    @Override
    public PresetBuilder buildMessage(CommandEvent event) {
        return new PresetBuilder(
            DefaultPresetType.ERROR,
            "This command requires a profile! Create one using `/start`.",
            "Requires Profile"
        );
    }

    public static boolean hasProfile(User user) {
        return Colossus.getDatabaseDriver().find(user) != null;
    }
}
