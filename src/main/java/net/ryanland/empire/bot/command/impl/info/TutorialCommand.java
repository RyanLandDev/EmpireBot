package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.TutorialArgument;
import net.ryanland.empire.bot.command.executor.CommandException;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.tutorials.Tutorial;
import net.ryanland.empire.bot.command.tutorials.TutorialMaker;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.interactions.tabmenu.TabMenuBuilder;

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
                        .name("tutorial")
                        .description("description")
        );
    }

    @Override
    public void run(CommandEvent event) {
        Tutorial tutorial = event.getArgument("tutorial");
    }

    private void supplyTutorialHelp(CommandEvent event, Tutorial tutorial) {
        event.reply(TutorialMaker.tutorialEmbed(event, tutorial)).queue();
    }
}
