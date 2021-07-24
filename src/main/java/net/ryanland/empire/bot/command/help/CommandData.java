package net.ryanland.empire.bot.command.help;

import net.ryanland.empire.bot.command.permissions.Permission;

public class CommandData {

    private String name;
    private String[] aliases = new String[0];
    private String description;
    private Category category;
    private Permission permission = Permission.USER;
    private int cooldown;
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

    public CommandData cooldown(int cooldown) {
        this.cooldown = cooldown;
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

    public int getCooldown() {
        return cooldown;
    }

    public boolean isProfileRequired() {
        return requiresProfile;
    }
}
