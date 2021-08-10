package net.ryanland.empire.bot.command.tutorials;

import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.List;

public class TutorialMaker {

    public static PresetBuilder tutorialEmbed(CommandEvent event, Tutorial tutorial) {

        return new PresetBuilder()
                .setTitle(tutorial.getName() + " Tutorial")
                .setDescription(tutorial.getDescription() + "\n\u200b")
                .addLogo()
                .addField("",tutorial.getBody());
    }
}
