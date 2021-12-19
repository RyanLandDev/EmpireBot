package net.ryanland.empire.bot.command.impl;

import net.ryanland.colossus.command.DefaultCommand;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.message.Emojis;

public abstract class GameCommand extends DefaultCommand implements Emojis {
    //TODO requires profile

    public void debug(Object msg) {
        if (Empire.debugMode)
            System.out.println(msg);
    }//TODO change to match old debug method

    public Profile getProfile(CommandEvent event) {
        return new Profile(event.getUser());
    }
}
