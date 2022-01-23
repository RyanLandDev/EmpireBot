package net.ryanland.empire.bot.command.impl.gameplay.games;

import net.ryanland.colossus.command.Category;
import net.ryanland.empire.bot.command.RequiresProfile;
import net.ryanland.empire.bot.command.impl.GameCommand;

public abstract class GamesCommand extends GameCommand implements RequiresProfile {

    @Override
    public Category getCategory() {
        return new Category("Games", "Play a few games and you may get lucky.", "🎯");
    }
}
