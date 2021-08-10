package net.ryanland.empire.bot.command.tutorials;

public enum Tutorial {

    TEST("test", "This is a test!", "Test etst setstsetse tset set set");

    private final String name;
    private final String description;
    private final String body;

    Tutorial(String name, String description, String body) {
        this.name = name;
        this.description = description;
        this.body = body;
    }

    public static Tutorial[] getTutorials() { return values(); }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }
}
