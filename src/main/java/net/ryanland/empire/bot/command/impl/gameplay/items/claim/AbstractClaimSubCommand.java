package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.empire.bot.command.executor.cooldown.CooldownHandler;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.message.builders.PresetType;

public abstract class AbstractClaimSubCommand extends Command {

    @Override
    public final ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    public abstract ClaimCommandInfo getClaimInfo();

    @Override
    public final void run(CommandEvent event) throws CommandException {
        ClaimCommandInfo info = getClaimInfo();

        try {
            if (info.check().check(event)) {
                info.claim().accept(event);
                event.reply(new PresetBuilder(PresetType.SUCCESS,
                    String.format("You have claimed your %s.\n%s %s", info.name(), ARROW_RIGHT, info.receiveMessage())
                ));
            } else {
                throw new CommandException("You cannot claim your %s.\n%s".formatted(info.name(), info.failMessage()));
            }
        } catch (CommandException e) {
            CooldownHandler.removeCooldown(event);
            throw e;
        }
    }
}
