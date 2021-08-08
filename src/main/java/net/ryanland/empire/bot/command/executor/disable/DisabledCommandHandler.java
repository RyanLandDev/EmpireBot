package net.ryanland.empire.bot.command.executor.disable;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.impl.Command;

import java.util.List;

public class DisabledCommandHandler {

    private static DisabledCommandHandler instance;

    public DisabledCommandHandler() {
        instance = this;
    }

    public static DisabledCommandHandler getInstance() {
        return instance == null ? new DisabledCommandHandler() : instance;
    }

    public List<Command> getDisabledCommands() {
        return Empire.getGlobalDocument().getDisabledCommands();
    }

    public List<String> getDisabledCommandsRaw() {
        return Empire.getGlobalDocument().getDisabledCommandsRaw();
    }

    public boolean isDisabled(Command command) {
        return getDisabledCommands().contains(command);
    }

    public void enable(Command command) {
        enable(command.getName());
    }

    public void enable(String command) {
        List<String> disabled = getDisabledCommandsRaw();
        if (!disabled.contains(command)) {
            throw new IllegalStateException("This command is already enabled.");
        }
        disabled.remove(command);

        Empire.getGlobalDocument()
                .setDisabledCommandsRaw(disabled)
                .update();
    }

    public void disable(Command command) {
        disable(command.getName());
    }

    public void disable(String command) {
        List<String> disabled = getDisabledCommandsRaw();
        if (disabled.contains(command)) {
            throw new IllegalStateException("This command is already disabled.");
        }
        disabled.add(command);

        Empire.getGlobalDocument()
                .setDisabledCommandsRaw(disabled)
                .update();
    }
}
