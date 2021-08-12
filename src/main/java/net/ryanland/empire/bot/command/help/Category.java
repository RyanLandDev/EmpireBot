package net.ryanland.empire.bot.command.help;

public enum Category {

    INFORMATION("Information", "Commands to get general information.", "📋"),
    DEVELOPER("Developer", "Utility commands for bot developers only.", "💻"),
    EMPIRE("Empire","Commands that concern the actual game.","🎮");


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
