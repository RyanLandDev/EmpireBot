package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.Tutorial;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.tutorials.TutorialMaker;

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
                new EnumArgument<>(Tutorial.class)
                        .id("tutorial")
                        .description("The name of the tutorial.")
        );
    }

    @Override
    public void run(CommandEvent event) {
        Tutorial tutorial = event.getArgument("tutorial");

        event.reply(TutorialMaker.tutorialEmbed(event, tutorial));
    }

}