package net.ryanland.empire.bot.command.executor.checks.impl;

import net.ryanland.empire.bot.command.executor.checks.CommandCheck;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class RequiresProfileCheck implements CommandCheck {

    @Override
    public boolean check(CommandEvent event) {
        return event.getCommand().requiresProfile() && DocumentCache.get(event.getUser(), UserDocument.class, true) == null;
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
