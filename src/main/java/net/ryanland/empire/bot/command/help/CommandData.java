package net.ryanland.empire.bot.command.help;

import net.ryanland.empire.bot.command.executor.flags.Flag;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.sys.file.StorageType;

import java.util.Arrays;
import java.util.List;

public class CommandData {

    private String name;
    private String[] aliases = new String[0];
    private String description;
    private Category category;
    private Permission permission = Permission.USER;
    private Flag[] flags = new Flag[0];
    private int cooldown;
    private StorageType cooldownStorageType = StorageType.MEMORY;
    private boolean requiresProfile = false;

    public CommandData name(String name) {
        this.name = name;
        return this;
    }

    public CommandData aliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandData description(String description) {
        this.description = description;
        return this;
    }

    public CommandData category(Category category) {
        this.category = category;
        return this;
    }

    public CommandData permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public CommandData flags(Flag... flags) {
        this.flags = flags;
        return this;
    }

    public CommandData cooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public CommandData cooldownStorage(StorageType cooldownStorageType) {
        this.cooldownStorageType = cooldownStorageType;
        return this;
    }

    public CommandData requiresProfile() {
        this.requiresProfile = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
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
}
