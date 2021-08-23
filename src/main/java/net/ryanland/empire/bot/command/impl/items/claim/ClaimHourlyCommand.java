package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;

public class ClaimHourlyCommand extends SubCommand {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("hourly")
                .description("Claim your Hourly package.")
                .cooldown(3600)
                .cooldownStorage(StorageType.EXTERNAL);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {

    }
}
