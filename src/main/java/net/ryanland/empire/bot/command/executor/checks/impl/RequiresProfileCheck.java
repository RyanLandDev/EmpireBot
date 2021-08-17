package net.ryanland.empire.bot.command.executor.checks.impl;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class RequiresProfileCheck extends CommandCheck {

    @Override
    public boolean check(CommandEvent event) {
        return event.getCommand().requiresProfile() &&
                !hasProfile(event.getUser());
    }

    public static boolean hasProfile(User user) {
        return DocumentCache.get(user, UserDocument.class, true) != null;
    }

    @Override
    public PresetBuilder buildMessage(CommandEvent event) {
        return new PresetBuilder(
                PresetType.ERROR,
                "This command requires a profile! Create one using `/start`.",
                "Requires Profile"
        );
    }
}
