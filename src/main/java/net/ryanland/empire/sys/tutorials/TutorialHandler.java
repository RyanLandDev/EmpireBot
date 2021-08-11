package net.ryanland.empire.sys.tutorials;

import java.util.HashMap;

public class TutorialHandler {

    private static final HashMap<String, Tutorial> EXECUTORS = new HashMap<>();

    public static void register(Tutorial... tutorials) {
        for (Tutorial tutorial : tutorials) {
            System.out.println(tutorial.getExecutor());
            if (tutorial.getExecutor() == null || tutorial.getName() == null || tutorial.getDescription() == null || tutorial.getBody() == null ) {
                throw new IllegalStateException(tutorial.getClass().getName() +
                        "- Tutorials must have at least an executor, name, description, and body.");
            }

            EXECUTORS.put(tutorial.getExecutor(), tutorial);
        }
    }

    public static HashMap<String, Tutorial> getExecutors() { return EXECUTORS; }

    public static Tutorial getTutorial(String executor) { return EXECUTORS.get(executor); }
}
