package net.ryanland.empire.bot.command.impl.gameplay.items.claim;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.cooldown.CooldownHandler;
import net.ryanland.colossus.command.cooldown.CooldownManager;
import net.ryanland.colossus.command.cooldown.DatabaseCooldownManager;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.SubCommand;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.impl.gameplay.items.ItemsCommand;
import net.ryanland.empire.sys.message.Emojis;

public abstract class AbstractClaimSubCommand extends ItemsCommand implements SubCommand, CombinedCommand {

    @Override
    public CooldownManager getCooldownManager() {
        return DatabaseCooldownManager.getInstance();
    }

    @Override
    public final ArgumentSet getArguments() {
        return null;
    }

    public abstract ClaimCommandInfo getClaimInfo();

    @Override
    public final void execute(CommandEvent event) throws CommandException {
        ClaimCommandInfo info = getClaimInfo();

        try {
            if (info.check().test(event)) {
                info.claim().accept(event);
                event.reply(new PresetBuilder(DefaultPresetType.SUCCESS,
                    String.format("You have claimed your %s.\n%s %s", info.name(), Emojis.ARROW_RIGHT, info.receiveMessage())
                ));
            } else {
                throw new CommandException("You cannot claim your %s.\n%s".formatted(info.name(), info.failMessage()));
            }
        } catch (CommandException e) {
            CooldownHandler.removeCooldown(event.getCommand().getCooldownManager(), event.getUser(), event.getCommand());
            throw e;
        }
    }
}
