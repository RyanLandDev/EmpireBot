package net.ryanland.empire.bot.command.impl;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SubCommandGroup {
    private final String name;
    private final String description;
    private final List<SubCommand> subCommands;

    public SubCommandGroup(@NotNull String name, @NotNull String description, @NotNull SubCommand... subCommands) {
        this.name = name;
        this.description = description;
        this.subCommands = Arrays.asList(subCommands);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }
}
