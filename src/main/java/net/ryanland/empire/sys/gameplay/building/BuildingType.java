package net.ryanland.empire.sys.gameplay.building;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import org.jetbrains.annotations.NotNull;

public enum BuildingType {

    RESOURCE("Resource", "<:resource:878654031009046558>"),
    DEFENSE("Defense", "<:defense:878653834333921361>"),

    RESOURCE_GENERATOR("Generator", RESOURCE, "<:resource_generator:878653505408213052>"),
    RESOURCE_STORAGE("Storage", RESOURCE, "<:resource_storage:878654242204815400>"),

    DEFENSE_THORNED("Thorned", DEFENSE, "<:defense_thorned:878654523655200778>"),
    DEFENSE_RANGED("Ranged", DEFENSE, "<:defense_ranged:878654639774523403>");

    private final String name;
    private final String emoji;
    private final BuildingType baseType;

    BuildingType(String name, String emoji) {
        this(name, null, emoji);
    }

    BuildingType(String name, BuildingType baseType, String emoji) {
        this.name = name;
        this.baseType = baseType;
        this.emoji = emoji;
    }

    public BuildingType getBaseType() {
        return baseType;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getFullName() {
        return getBaseType().getName() + " / " + getName();
    }

    public PresetBuilder getEmbed(@NotNull Profile profile) {
        return new PresetBuilder()
            .setTitle(getEmoji() + " Building Shop")
            .setDescription(String.format("You have %s and %s.\nYou can buy items using `/buy <id>`.",
                profile.getGold().format(), profile.getCrystals().format()))
            .addFields(Building.getBuildingsInstances().stream()
                .filter(building -> building.getType() == this)
                .map(Building::getEmbedField)
                .toArray(MessageEmbed.Field[]::new));
    }

    public boolean isBaseType() {
        return getBaseType() == null;
    }
}
