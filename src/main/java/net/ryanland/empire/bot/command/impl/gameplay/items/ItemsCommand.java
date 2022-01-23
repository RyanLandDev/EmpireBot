package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.Category;
import net.ryanland.empire.bot.command.RequiresProfile;
import net.ryanland.empire.bot.command.impl.GameCommand;

public abstract class ItemsCommand extends GameCommand implements RequiresProfile {

    @Override
    public Category getCategory() {
        return new Category("Items", "Commands regarding items in your empire.", "🏠");
    }
}
