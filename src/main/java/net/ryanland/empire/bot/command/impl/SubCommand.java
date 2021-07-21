package net.ryanland.empire.bot.command.impl;

public abstract class SubCommand extends Command {

    @Override
    public String getDescription() {
        throw new IllegalStateException();
    }
}
