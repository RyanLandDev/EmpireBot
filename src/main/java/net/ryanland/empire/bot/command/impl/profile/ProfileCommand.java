package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.colossus.command.Category;
import net.ryanland.empire.bot.command.impl.GameCommand;

public abstract class ProfileCommand extends GameCommand {

    @Override
    public Category getCategory() {
        return new Category("Profile", "Commands that concern the user profile.", "🏰");
    }
}
