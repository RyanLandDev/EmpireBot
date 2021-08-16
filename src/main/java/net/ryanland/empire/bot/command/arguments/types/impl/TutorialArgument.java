package net.ryanland.empire.bot.command.arguments.types.impl;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.sys.tutorials.Tutorial;
import net.ryanland.empire.sys.tutorials.TutorialHandler;
import net.ryanland.empire.bot.events.CommandEvent;

public class TutorialArgument extends SingleArgument<Tutorial> {

    @Override
    public Tutorial parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        Tutorial tutorial = TutorialHandler.getTutorial(argument.getAsString());

        if (tutorial == null) {
            throw new MalformedArgumentException(
                    "This tutorial was not found."
            );
        } else {
            return tutorial;
        }
    }
}
