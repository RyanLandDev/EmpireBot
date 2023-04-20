package net.ryanland.empire.bot.command.impl;

import net.ryanland.colossus.command.BaseCommand;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.message.Emojis;

public abstract class GameCommand extends BaseCommand implements Emojis {

    public static void debug(Object msg) {
        if (Empire.debugMode) System.out.println("[DEBUG] " + msg);
    }

    public Profile getProfile(CommandEvent event) {
        return Profile.of(event);
    }
}
