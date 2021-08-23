package net.ryanland.empire.bot.command.impl.items.claim;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public abstract class AbstractClaimSubCommand extends SubCommand {

    @Override
    public final ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    public abstract ClaimCommandInfo getClaimInfo();

    @Override
    public final void run(CommandEvent event) throws CommandException {
        ClaimCommandInfo info = getClaimInfo();

        if (info.check().test(event)) {
            info.claim().accept(event);
            event.reply(new PresetBuilder(PresetType.SUCCESS,
                    String.format("You have claimed your %s.\n%s %s", info.name(), ARROW_RIGHT, info.receiveMessage())
            ));
        } else {
            event.reply(new PresetBuilder(PresetType.ERROR,
                    String.format("You cannot claim your %s.\n%s", info.name(), info.failMessage())
            ));
        }
    }
}
