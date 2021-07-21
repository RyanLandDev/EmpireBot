package net.ryanland.empire.bot.command.impl;

public abstract class SubCommand extends Command {

    @Override
    public String getDescription() {
        throw new IllegalStateException();
    }

    @Override
    public boolean userExecutable() {
        return false;
    }
}
