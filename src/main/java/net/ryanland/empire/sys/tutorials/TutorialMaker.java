package net.ryanland.empire.sys.tutorials;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class TutorialMaker {

    private final static String DEFAULT_THUMBNAIL = "https://upload.wikimedia.org/wikipedia/commons/4/43/Minimalist_info_Icon.png";

    public static PresetBuilder tutorialEmbed(CommandEvent event, Tutorial tutorial) {

        return new PresetBuilder()
                .setTitle(tutorial.getName())
                .setDescription("*"+tutorial.getDescription()+"*")
                .addLogo()
                .addField("",tutorial.getBody())
                .setThumbnail((tutorial.hasThumbnail()) ? (tutorial.getThumbnail()) : DEFAULT_THUMBNAIL);
    }
}
