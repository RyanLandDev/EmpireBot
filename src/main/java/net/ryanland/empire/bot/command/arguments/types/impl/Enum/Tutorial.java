package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.ryanland.empire.Empire;

public enum Tutorial implements EnumArgument.InputEnum {

    // Remember to add it to Empire.java lol
    // %field% to create new fields.
    CONTRIBUTE("contribute", "How to Contribute", "Want to help out the bot?", "",
        new MessageEmbed.Field("", "We have a [GitHub repository](" + Empire.GITHUB_LINK + ") you can contribute to and a support Discord where you can ask questions.", false)),
    ;

    private final String title;
    private final String embedTitle;
    private final String description;
    private final MessageEmbed.Field[] fields;
    private final String thumbnail;

    Tutorial(String title, String embedTitle, String description, String thumbnail, MessageEmbed.Field... fields) {
        this.title = title;
        this.embedTitle = embedTitle;
        this.description = description;
        this.fields = fields;
        this.thumbnail = thumbnail;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getEmbedTitle() {
        return embedTitle;
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