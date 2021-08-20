package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.Tutorial;
import net.ryanland.empire.sys.tutorials.TutorialMaker;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

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
                new EnumArgument<Tutorial>().setEnum(Tutorial.class)
                        .id("tutorial")
                        .description("The name of the tutorial.")
                        .optional()
        );
    }

    @Override
    public void run(CommandEvent event) {
        Tutorial tutorial = event.getArgument("tutorial");

        if (tutorial == null) {
            event.reply(new PresetBuilder(
                    "Tutorials give you a brief overview of some of the mechanics of the bot.\n",
                    "Tutorials")
                    .addField("Tutorials",
                            "`" +
                                    Arrays.stream(Tutorial.values())
                                            .map(Tutorial::getTitle)
                                            .collect(Collectors.joining("` `")) + "`",
                            true));
        } else {
            supplyTutorialHelp(event, tutorial);
        }
    }

    private void supplyTutorialHelp(CommandEvent event, Tutorial tutorial) {
        event.reply(TutorialMaker.tutorialEmbed(event, tutorial));
    }
}
