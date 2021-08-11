package net.ryanland.empire.bot.command.tutorials;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class TutorialMaker {

    private final static String DEFAULT_THUMBNAIL = "https://upload.wikimedia.org/wikipedia/commons/4/43/Minimalist_info_Icon.png";

    public static PresetBuilder tutorialEmbed(CommandEvent event, Tutorial tutorial) {
        PresetBuilder builder = new PresetBuilder();

        MessageEmbed.Field[] fields = tutorial.getFields();

        builder
                .setTitle(tutorial.getName())
                .setDescription("*"+tutorial.getDescription()+"*")
                .addLogo()
                .setThumbnail((tutorial.hasThumbnail()) ? (tutorial.getThumbnail()) : DEFAULT_THUMBNAIL);

        for (MessageEmbed.Field field : fields) {
            builder.addField(field.getName(), field.getValue(), field.isInline());
        }

        return builder;
    }
}
