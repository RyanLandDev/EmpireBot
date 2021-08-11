package net.ryanland.empire.sys.tutorials;

public enum Tutorial {

    CONTRIBUTE("contribute","How to Contribute", "Want to help out the bot?", "We have a support server and github repo. gl finding them ig",""),
    BEST("best","How to identify an amazing person","It's a necessary life skill.", "If their name starts with General_ and ends with Mudkip you've found them.","https://cdn.discordapp.com/emojis/774263473483153418.png?v=1");

    private final String executor;
    private final String name;
    private final String description;
    private final String body;
    private final String thumbnail;

    Tutorial(String executor, String name, String description, String body, String thumbnail) {
        this.executor = executor;
        this.name = name;
        this.description = description;
        this.body = body;
        this.thumbnail = thumbnail;
    }

    public String getExecutor() { return executor; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getBody() { return body; }

    public String getThumbnail() { return thumbnail; }

    public Boolean hasThumbnail() { return !thumbnail.isEmpty(); }
}