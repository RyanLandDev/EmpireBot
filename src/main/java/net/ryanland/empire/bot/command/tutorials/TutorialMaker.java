package net.ryanland.empire.bot.command.tutorials;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class TutorialMaker {

    private final static String DEFAULT_THUMBNAIL = "https://upload.wikimedia.org/wikipedia/commons/4/43/Minimalist_info_Icon.png";

    public static PresetBuilder tutorialEmbed(CommandEvent event, Tutorial tutorial) {
        PresetBuilder builder = new PresetBuilder();

        String body = tutorial.getBody();

        builder
                .setTitle(tutorial.getName())
                .setDescription("*"+tutorial.getDescription()+"*")
                .addLogo()
                .setThumbnail((tutorial.hasThumbnail()) ? (tutorial.getThumbnail()) : DEFAULT_THUMBNAIL);

        // Checks if only 1 field needs to be made or not
        if (body.contains("%field%")) {
            // If not, splits the body by %field% and iterates through the array adding new fields.
            String[] fieldValues = body.split("%field%");
            System.out.println(fieldValues);
            for (String value : fieldValues) {
                builder.addField("",value,false);
            }
        } else {
            builder.addField("",body);
        }

        return builder;
    }
}
