package net.ryanland.empire.sys.tutorials;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.ryanland.empire.sys.message.Emojis;

public enum Tutorial {

    // Remember to add it to Empire.java lol
    // %field% to create new fields.
    CONTRIBUTE("contribute","How to Contribute", "Want to help out the bot?", "",
            new MessageEmbed.Field("","We have a GitHub repository you can contribute to and a support Discord where you can ask questions.",false)),
    ;

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

    public String getExecutor() {
        return executor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Boolean hasThumbnail() {
        return !thumbnail.isEmpty();
    }

    public MessageEmbed.Field[] getFields() {
        return fields;
    }

}