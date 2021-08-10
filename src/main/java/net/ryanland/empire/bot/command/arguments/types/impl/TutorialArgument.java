package net.ryanland.empire.bot.command.arguments.types.impl;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.command.impl.TutorialHandler;
import net.ryanland.empire.bot.command.tutorials.Tutorial;
import net.ryanland.empire.bot.events.CommandEvent;

public class TutorialArgument extends SingleArgument<Tutorial> {

    public Tutorial parsed(String name) throws ArgumentException {
        Tutorial tutorial = TutorialHandler.getTutorial(name);

        if (tutorial == null) {
            throw new MalformedArgumentException(
                    "This tutorial was not found."
            );
        } else {
            return tutorial;
        }
    }

    @Override
    public Tutorial parsed(String argument, CommandEvent event) throws ArgumentException {
        return null;
    }
}
