package net.ryanland.empire.bot.command.info;

import net.ryanland.empire.bot.command.executor.data.Flag;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.sys.file.StorageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandInfo {

    private String name;
    private String description;
    private Category category;
    private Permission permission = Permission.USER;
    private Flag[] flags = new Flag[0];
    private int cooldown;
    private StorageType cooldownStorageType = StorageType.MEMORY;
    private boolean requiresProfile = false;

    private List<SubCommand> subCommands = new ArrayList<>();
    private List<SubCommandGroup> subCommandGroups = new ArrayList<>();
    private HashMap<String, SubCommand> subCommandMap;
    private HashMap<String, SubCommandGroup> subCommandGroupMap;

    public CommandInfo name(String name) {
        this.name = name;
        return this;
    }

    public CommandInfo description(String description) {
        this.description = description;
        return this;
    }

    public CommandInfo category(Category category) {
        this.category = category;
        return this;
    }

    public CommandInfo permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public CommandInfo flags(Flag... flags) {
        this.flags = flags;
        return this;
    }

    /**
     * The cooldown set for this command.
     *
     * @param cooldown Cooldown in seconds.
     * @return The {@link CommandInfo} for chaining.
     * @see #cooldownStorage(StorageType)
     */
    public CommandInfo cooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public CommandInfo cooldownStorage(StorageType cooldownStorageType) {
        this.cooldownStorageType = cooldownStorageType;
        return this;
    }

    public CommandInfo requiresProfile() {
        requiresProfile = true;
        return this;
    }

    public CommandInfo subCommands(SubCommand... subCommands) {
        this.subCommands = Arrays.asList(subCommands);
        subCommandMap = new HashMap<>();
        for (SubCommand cmd : subCommands) {
            subCommandMap.put(cmd.getName(), cmd);
        }
        return this;
    }

    public CommandInfo subCommandGroups(SubCommandGroup... subCommandGroups) {
        this.subCommandGroups = Arrays.asList(subCommandGroups);
        subCommandGroupMap = new HashMap<>();
        for (SubCommandGroup group : subCommandGroups) {
            subCommandGroupMap.put(group.getName(), group);
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Permission getPermission() {
        return permission;
    }

    public Flag[] getFlags() {
        return flags;
    }

    public List<Flag> getFlagsAsList() {
        return Arrays.asList(getFlags());
    }

    public boolean flagsContain(Flag flag) {
        return getFlagsAsList().contains(flag);
    }

    public int getCooldown() {
        return cooldown;
    }

    public StorageType getCooldownStorageType() {
        return cooldownStorageType;
    }

    public boolean isProfileRequired() {
        return requiresProfile;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public List<SubCommandGroup> getSubCommandGroups() {
        return subCommandGroups;
    }

    public HashMap<String, SubCommand> getSubCommandMap() {
        return subCommandMap;
    }

    public HashMap<String, SubCommandGroup> getSubCommandGroupMap() {
        return subCommandGroupMap;
    }

}
