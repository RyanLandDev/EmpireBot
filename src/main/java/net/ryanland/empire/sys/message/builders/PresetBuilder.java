package net.ryanland.empire.sys.message.builders;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.LinkedList;
import java.util.List;

public class PresetBuilder {

    private int color;
    private String description;
    private String title;
    private String authorName;
    private String authorUrl;
    private String authorIconUrl;
    private String footer;
    private String footerIconUrl;
    private String image;
    private String thumbnail;
    private List<MessageEmbed.Field> fields = new LinkedList<>();

    public PresetBuilder(String description) {
        PresetType type = PresetType.DEFAULT;
        this.color = type.getColor();
        this.description = description;
        this.title = type.getDefaultTitle();
    }

    public PresetBuilder(String description, String title) {
        PresetType type = PresetType.DEFAULT;
        this.color = type.getColor();
        this.description = description;
        this.title = title;
    }

    public PresetBuilder(PresetType type) {
        this.color = type.getColor();
        this.description = "";
        this.title = type.getDefaultTitle();
    }

    public PresetBuilder(PresetType type, String description) {
        this.color = type.getColor();
        this.description = description;
        this.title = type.getDefaultTitle();
    }

    public PresetBuilder(PresetType type, String description, String title) {
        this.color = type.getColor();
        this.description = description;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public PresetBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PresetBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PresetBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public PresetBuilder setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public PresetBuilder setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
        return this;
    }

    public String getAuthorIconUrl() {
        return authorIconUrl;
    }

    public PresetBuilder setAuthorIconUrl(String authorIconUrl) {
        this.authorIconUrl = authorIconUrl;
        return this;
    }

    public String getFooter() {
        return footer;
    }

    public PresetBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public String getFooterIconUrl() {
        return footerIconUrl;
    }

    public PresetBuilder setFooterIconUrl(String footerIconUrl) {
        this.footerIconUrl = footerIconUrl;
        return this;
    }

    public String getImage() {
        return image;
    }

    public PresetBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public PresetBuilder setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public List<MessageEmbed.Field> getFields() {
        return fields;
    }

    public PresetBuilder setFields(List<MessageEmbed.Field> fields) {
        this.fields = fields;
        return this;
    }

    public PresetBuilder addField(String name, String value, boolean inline) {
        this.fields.add(new MessageEmbed.Field(name, value, inline));
        return this;
    }

    public MessageEmbed build() {
        EmbedBuilder builder = new EmbedBuilder()
                .setDescription(description)
                .setTitle(title)
                .setFooter(footer, footerIconUrl)
                .setColor(color)
                .setAuthor(authorName, authorUrl, authorIconUrl);

        for (MessageEmbed.Field field : fields) {
            builder.addField(field);
        }
        return builder.build();
    }
}
