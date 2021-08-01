package net.ryanland.empire.bot.command.executor.disable;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.impl.Command;

import java.util.List;

// TODO test the disable and enable command
public class DisabledCommandHandler {

    public List<Command> getDisabledCommands() {
        return Empire.getGlobalDocument().getDisabledCommands();
    }

    public boolean isDisabled(Command command) {
        return getDisabledCommands().contains(command);
    }

    public void enable(Command command) {
        List<Command> disabled = getDisabledCommands();
        if (!disabled.contains(command)) {
            throw new IllegalStateException("This command is already enabled.");
        }
        disabled.remove(command);

        Empire.getGlobalDocument()
                .setDisabledCommands(disabled)
                .update();
    }

    public void disable(Command command) {
        List<Command> disabled = getDisabledCommands();
        if (disabled.contains(command)) {
            throw new IllegalStateException("This command is already disabled.");
        }
        disabled.add(command);

        Empire.getGlobalDocument()
                .setDisabledCommands(disabled)
                .update();
    }
}
