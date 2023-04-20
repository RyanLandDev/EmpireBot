package net.ryanland.empire.bot.command.inhibitor;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.command.inhibitors.Inhibitor;
import net.ryanland.colossus.events.command.BasicCommandEvent;
import net.ryanland.colossus.sys.file.database.PrimaryKey;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.RequiresProfile;
import net.ryanland.empire.sys.message.builders.Preset;

import java.util.Map;

public class RequiresProfileInhibitor implements Inhibitor {

    @Override
    public boolean check(BasicCommandEvent event) {
        return event.getCommand() instanceof RequiresProfile && hasProfile(event.getUser());
    }

    @Override
    public PresetBuilder buildMessage(BasicCommandEvent event) {
        return new PresetBuilder(
            Preset.ERROR,
            "This command requires a profile! Create one using `/start`.",
            "Requires Profile"
        );
    }

    public static boolean hasProfile(User user) {
        return Colossus.getDatabaseDriver().get("users").getSuppliers().get(
            new PrimaryKey(Map.of("_user_id", user.getId()))) != null;
    }
}
