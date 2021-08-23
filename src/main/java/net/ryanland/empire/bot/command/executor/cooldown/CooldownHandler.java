package net.ryanland.empire.bot.command.executor.cooldown;

import net.ryanland.empire.bot.command.executor.cooldown.manager.CooldownManager;
import net.ryanland.empire.bot.command.executor.cooldown.manager.ExternalCooldownManager;
import net.ryanland.empire.bot.command.executor.cooldown.manager.MemoryCooldownManager;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CooldownHandler {

    public static CooldownManager getCooldownManager(StorageType storage) {
        if (storage == StorageType.MEMORY) {
            return MemoryCooldownManager.getInstance();
        }
        if (storage == StorageType.LOCAL) {
            throw new RuntimeException();//TODO
        }
        if (storage == StorageType.EXTERNAL) {
            return ExternalCooldownManager.getInstance();
        }

        throw new IllegalArgumentException();
    }

    public static CooldownManager getCooldownManager(CommandEvent event) {
        return getCooldownManager(event.getCommand().getCooldownStorageType());
    }

    public static boolean isCooldownActive(CommandEvent event) {
        cleanCooldowns(event);
        return getActiveCooldowns(event).stream()
                .anyMatch(cooldown -> cooldown.command().getName().equals(event.getCommand().getName()));
    }

    public static Cooldown getActiveCooldown(CommandEvent event) {
        if (!isCooldownActive(event)) return null;
        return getActiveCooldowns(event).stream()
                .filter(cooldown -> cooldown.command().getName().equals(event.getCommand().getName()))
                .collect(Collectors.toList())
                .get(0);
    }

    public static void newCooldown(CommandEvent event) {
        cleanCooldowns(event);
        getCooldownManager(event).put(event.getUser(), new Cooldown(event.getCommand(),
                new Date(new Date().getTime() + event.getCommand().getCooldownInMs())));
    }

    public static List<Cooldown> getActiveCooldowns(CommandEvent event) {
        return getCooldownManager(event).get(event.getUser());
    }

    public static void cleanCooldowns(CommandEvent event) {
        Date date = new Date();
        List<Cooldown> activeCooldowns = getActiveCooldowns(event);
        List<Cooldown> cooldowns = activeCooldowns.stream()
                .filter(cooldown -> date.before(cooldown.expires()))
                .collect(Collectors.toList());

        if (!activeCooldowns.equals(cooldowns)) {
            if (cooldowns.isEmpty()) {
                purgeCooldowns(event);
            } else {
                getCooldownManager(event).put(event.getUser(), cooldowns);
            }
        }
    }

    public static void purgeCooldowns(CommandEvent event) {
        getCooldownManager(event).purge(event.getUser());
    }
}
