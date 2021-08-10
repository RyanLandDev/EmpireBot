package net.ryanland.empire.bot.command.impl.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.TutorialArgument;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.TutorialHandler;
import net.ryanland.empire.bot.command.tutorials.Tutorial;
import net.ryanland.empire.bot.command.tutorials.TutorialMaker;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Arrays;

public class TutorialCommand extends Command {
    @Override
    public CommandData getData() {
        return new CommandData()
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
                event.reply(new PresetBuilder(
                        PresetType.ERROR, "Tutorial not found.", "Not found"
                )).setEphemeral(true).queue();
            } else {
                supplyTutorialHelp(event, tutorial);
            }

        } catch (IndexOutOfBoundsException e) {
            String tutorialList = "";
            for (Tutorial tutorial : Tutorial.getTutorials()) {
                tutorialList += String.format("`%s` ",tutorial.getExecutor());
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder
                    .setTitle("Tutorials")
                    .setDescription("Tutorials give you a brief overview of some of the mechanics of the bot.\n")
                    .addField("Tutorials", tutorialList, true);

            event.reply(builder.build()).queue();
        }
    }

    private void supplyTutorialHelp(CommandEvent event, Tutorial tutorial) {
        event.reply(TutorialMaker.tutorialEmbed(event, tutorial).build()).queue();
    }
}
