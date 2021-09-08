package net.ryanland.empire.bot.command.info;

public enum Category {

    INFORMATION("Information", "Commands to get general information.", "📋"),
    DEVELOPER("Developer", "Utility commands for bot developers only.", "💻"),
    PROFILE("Profile", "Commands that concern the user profile.", "🏰"),

    ITEMS("Items", "Commands regarding items in your empire.", "🏠"),
    COMBAT("Combat", "Get out on the field and combat enemies.", "⚔"),
    GAMES("Games", "Play a few games and you may get lucky.", "🎯");

    private final String name;
    private final String description;
    private final String emoji;

    Category(String name, String description, String emoji) {
        this.name = name;
        this.description = description;
        this.emoji = emoji;
    }

    public static Category[] getCategories() {
        return values();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmoji() {
        return emoji;
    }
}
