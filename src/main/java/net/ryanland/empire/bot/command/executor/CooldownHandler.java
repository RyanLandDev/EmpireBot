package net.ryanland.empire.bot.command.executor;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CooldownHandler {

    private static final HashMap<User, List<Cooldown>> USER_COOLDOWNS = new HashMap<>();

    public static boolean isCooldownActive(CommandEvent event) {
        cleanCooldowns(event.getUser());

        List<Cooldown> cooldowns = new ArrayList<>(getActiveCooldowns(event.getUser()));
        cooldowns.removeIf(c -> !c.command.getName().equals(event.getCommand().getName()));

        return cooldowns.size() > 0;
    }

    public static void newCooldown(CommandEvent event) {
        cleanCooldowns(event.getUser());

        User user = event.getUser();
        List<Cooldown> cooldowns = getActiveCooldowns(user);

        cooldowns.add(new Cooldown(event.getCommand(),
                new Date(new Date().getTime() + event.getCommand().getCooldownInMs())));

        USER_COOLDOWNS.put(user, cooldowns);
    }

    public static List<Cooldown> getActiveCooldowns(User user) {
        List<Cooldown> cooldowns = USER_COOLDOWNS.get(user);
        if (cooldowns == null) cooldowns = new ArrayList<>();

        return cooldowns;
    }

    public static void cleanCooldowns(User user) {
        List<Cooldown> cooldowns = getActiveCooldowns(user);
        Date date = new Date();
        cooldowns.removeIf(cooldown -> date.after(cooldown.expires));

        if (cooldowns.size() == 0) {
            purgeCooldowns(user);
        }
    }

    public static HashMap<User, List<Cooldown>> getAllCooldowns() {
        return USER_COOLDOWNS;
    }

    public static void purgeCooldowns(User user) {
        USER_COOLDOWNS.remove(user);
    }

    public static void purgeCooldowns() {
        USER_COOLDOWNS.clear();
    }

    private record Cooldown(Command command, Date expires) {
    }
}
