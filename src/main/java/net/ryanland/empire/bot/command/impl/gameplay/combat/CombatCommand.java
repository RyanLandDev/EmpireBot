package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.colossus.command.Category;
import net.ryanland.empire.bot.command.impl.GameCommand;

public abstract class CombatCommand extends GameCommand {

    @Override
    public Category getCategory() {
        return new Category("Combat", "Get out on the field and combat enemies.", "⚔");
    }
}
