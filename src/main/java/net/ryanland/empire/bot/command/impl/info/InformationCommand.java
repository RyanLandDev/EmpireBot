package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.colossus.command.Category;
import net.ryanland.colossus.command.DefaultCommand;

public abstract class InformationCommand extends DefaultCommand {

    @Override
    public Category getCategory() {
        return new Category("Information", "Commands to get general information.", "📋");
    }
}
