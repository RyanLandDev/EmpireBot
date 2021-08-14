package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.HelpMaker;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.List;

public abstract class SubCommandHolder extends Command {

    @Override
    public final ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public final void run(CommandEvent event) {

    }

}
