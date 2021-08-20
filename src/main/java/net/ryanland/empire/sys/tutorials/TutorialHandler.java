package net.ryanland.empire.sys.tutorials;

import net.ryanland.empire.bot.command.arguments.types.impl.Enum.Tutorial;

import java.util.HashMap;

public class TutorialHandler {

    private static final HashMap<String, Tutorial> EXECUTORS = new HashMap<>();

    static {
        for (Tutorial tutorial : Tutorial.values()) {
            if (tutorial.getTitle() == null || tutorial.getEmbedTitle() == null || tutorial.getDescription() == null || tutorial.getFields() == null ) {
                throw new IllegalStateException(tutorial.getClass().getName() +
                        "- Tutorials must have at least an executor, name, description, and body.");
            }

            EXECUTORS.put(tutorial.getTitle(), tutorial);
        }
    }

    public static HashMap<String, Tutorial> getExecutors() {
        return EXECUTORS;
    }

    public static Tutorial getTutorial(String executor) {
        return EXECUTORS.get(executor);
    }
}