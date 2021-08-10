package net.ryanland.empire.bot.command;

import net.ryanland.empire.bot.command.tutorials.Tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorialHandler {

    private static final List<Tutorial> TUTORIALS = new ArrayList<>();
    private static final HashMap<String, Tutorial> EXECUTORS = new HashMap<>();

    public static void register(Tutorial... tutorials) {
        for (Tutorial tutorial : tutorials) {
            System.out.println(tutorial.getExecutor());
            if (tutorial.getExecutor() == null || tutorial.getName() == null || tutorial.getDescription() == null || tutorial.getBody() == null ) {
                throw new IllegalStateException(tutorial.getClass().getName() +
                        "- Tutorials must have at least an executor, name, description, and body.");
            }

            TUTORIALS.add(tutorial);
            EXECUTORS.put(tutorial.getExecutor(), tutorial);
        }
    }

    public static List<Tutorial> getTutorials() { return TUTORIALS; }

    public static HashMap<String, Tutorial> getExecutors() { return EXECUTORS; }

    public static Tutorial getTutorial(String executor) { return EXECUTORS.get(executor); }
}
