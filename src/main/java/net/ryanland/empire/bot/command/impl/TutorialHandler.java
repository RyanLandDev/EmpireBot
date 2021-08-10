package net.ryanland.empire.bot.command.impl;

import net.ryanland.empire.bot.command.tutorials.Tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorialHandler {

    private static final List<Tutorial> TUTORIALS = new ArrayList<>();
    private static final HashMap<String, Tutorial> NAMES = new HashMap<>();

    public static void register(Tutorial... tutorials) {
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getName() == null || tutorial.getDescription() == null || tutorial.getBody() == null ) {
                throw new IllegalStateException(tutorial.getClass().getName() +
                        "- Tutorials must have at least a name, description, and body.");
            }

            TUTORIALS.add(tutorial);
            NAMES.put(tutorial.getName(), tutorial);
        }
    }

    public static List<Tutorial> getTutorials() { return TUTORIALS; }

    public static Tutorial getTutorial(String name) { return NAMES.get(name); }
}
