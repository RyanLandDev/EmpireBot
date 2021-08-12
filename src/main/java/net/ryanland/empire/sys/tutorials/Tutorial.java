package net.ryanland.empire.sys.tutorials;

import net.dv8tion.jda.api.entities.MessageEmbed;

public enum Tutorial {

    // Remember to add it to Empire.java lol
    // %field% to create new fields.
    CONTRIBUTE("contribute","How to Contribute", "Want to help out the bot?", "",
            new MessageEmbed.Field("","We have a GitHub repository you can contribute to and a support Discord where you can ask questions.",false)),
    BEST("best","How to identify an amazing person","It's a necessary life skill.", "https://cdn.discordapp.com/emojis/774263473483153418.png?v=1",
            new MessageEmbed.Field("","If they name start with General_Mudkip and end with  you found them.",false),
            new MessageEmbed.Field("hee","HEEEEEEE",true)),
    PULL_REQUEST("pr","MERGE MY PULL REQUEST","NOW","",
            new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true), new MessageEmbed.Field("MERGE IT", "MERGE IT", true));
    private final String executor;
    private final String name;
    private final String description;
    private final MessageEmbed.Field[] fields;
    private final String thumbnail;

    Tutorial(String executor, String name, String description, String thumbnail, MessageEmbed.Field... fields) {
        this.executor = executor;
        this.name = name;
        this.description = description;
        this.fields = fields;
        this.thumbnail = thumbnail;
    }

    public String getExecutor() { return executor; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getThumbnail() { return thumbnail; }

    public Boolean hasThumbnail() { return !thumbnail.isEmpty(); }

    public MessageEmbed.Field[] getFields() { return fields; }

}