package net.ryanland.empire.bot.command.executor.data;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CooldownHandler {

    private static final HashMap<User, List<Cooldown>> MEMORY_USER_COOLDOWNS = new HashMap<>();

    public static HashMap<User, List<Cooldown>> getCooldowns(StorageType storage) {
        if (storage == StorageType.MEMORY) {
            return MEMORY_USER_COOLDOWNS;
        }
        if (storage == StorageType.LOCAL) {
            return null;//TODO
        }
        if (storage == StorageType.EXTERNAL) {
            return null;//TODO
        }

        throw new IllegalStateException();
    }

    public static HashMap<User, List<Cooldown>> getCooldowns(CommandEvent event) {
        return getCooldowns(event.getCommand().getCooldownStorageType());
    }

    public static boolean isCooldownActive(CommandEvent event) {
        cleanCooldowns(event);

        List<Cooldown> cooldowns = new ArrayList<>(getActiveCooldowns(event));
        cooldowns.removeIf(c -> !c.command.getName().equals(event.getCommand().getName()));

        return cooldowns.size() > 0;
    }

    public static void newCooldown(CommandEvent event) {
        cleanCooldowns(event);

        User user = event.getUser();
        List<Cooldown> cooldowns = getActiveCooldowns(event);

        cooldowns.add(new Cooldown(event.getCommand(),
                new Date(new Date().getTime() + event.getCommand().getCooldownInMs())));

        getCooldowns(event).put(user, cooldowns);
    }

    public static List<Cooldown> getActiveCooldowns(CommandEvent event) {
        List<Cooldown> cooldowns = getCooldowns(event).get(event.getUser());
        if (cooldowns == null) cooldowns = new ArrayList<>();

        return cooldowns;
    }

    public static void cleanCooldowns(CommandEvent event) {
        List<Cooldown> cooldowns = getActiveCooldowns(event);
        Date date = new Date();
        cooldowns.removeIf(cooldown -> date.after(cooldown.expires));

        if (cooldowns.size() == 0) {
            purgeCooldowns(event);
        }
    }

    public static void purgeCooldowns(CommandEvent event) {
        getCooldowns(event).remove(event.getUser());
    }

    public static void purgeCooldowns(StorageType storage) {
        getCooldowns(storage).clear();
    }

    private record Cooldown(Command command, Date expires) {
    }
}
