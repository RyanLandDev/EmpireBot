package net.ryanland.empire.bot.command.impl.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.TutorialArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.tutorials.Tutorial;
import net.ryanland.empire.sys.tutorials.TutorialHandler;
import net.ryanland.empire.sys.tutorials.TutorialMaker;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TutorialCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("tutorial")
                .description("A set of tutorials explaining the mechanics of the bot.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new TutorialArgument()
                        .id("tutorial")
                        .name("tutorial")
                        .description("The name of the tutorial.")
                        .optional()
        );
    }

    @Override
    public void run(CommandEvent event) {

        try {
            String tutorialName = event.getRawArgs()[0];

            Tutorial tutorial = TutorialHandler.getTutorial(tutorialName);

            if (tutorial == null) {
                event.performReply(new PresetBuilder(
                        PresetType.ERROR, "Tutorial not found.", "Not found"
                )).queue();
            } else {
                supplyTutorialHelp(event, tutorial);
            }

        } catch (IndexOutOfBoundsException e) {
            event.reply(new PresetBuilder(
                    "Tutorials give you a brief overview of some of the mechanics of the bot.\n",
                    "Tutorials")
                    .addField("Tutorials",
                            "`" +
                                    Arrays.stream(Tutorial.values())
                                    .map(Tutorial::getExecutor)
                                    .collect(Collectors.joining("` `")) + "`",
                            true));
        }
    }

    private void supplyTutorialHelp(CommandEvent event, Tutorial tutorial) {
        event.reply(TutorialMaker.tutorialEmbed(event, tutorial));
    }
}
