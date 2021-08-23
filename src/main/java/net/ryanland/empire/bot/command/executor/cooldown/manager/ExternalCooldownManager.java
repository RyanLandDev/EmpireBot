package net.ryanland.empire.bot.command.executor.cooldown.manager;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.executor.cooldown.Cooldown;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;

import java.util.ArrayList;
import java.util.List;

public class ExternalCooldownManager implements CooldownManager {

    private static final ExternalCooldownManager INSTANCE = new ExternalCooldownManager();

    public static ExternalCooldownManager getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Cooldown> get(User user) {
        return new Profile(user).getCooldowns();
    }

    @Override
    public List<Cooldown> put(User user, List<Cooldown> cooldowns) {
        DocumentCache.get(user, UserDocument.class).setCooldowns(cooldowns);
        DocumentCache.update(user, UserDocument.class);
        return cooldowns;
    }

    @Override
    public Cooldown put(User user, Cooldown cooldown) {
        List<Cooldown> cooldowns = new ArrayList<>(get(user));
        cooldowns.add(cooldown);

        DocumentCache.get(user, UserDocument.class).setCooldowns(cooldowns);
        DocumentCache.update(user, UserDocument.class);
        return cooldown;
    }

    @Override
    public Cooldown remove(User user, Cooldown cooldown) {
        List<Cooldown> cooldowns = new ArrayList<>(get(user));
        cooldowns.remove(cooldown);

        DocumentCache.get(user, UserDocument.class).setCooldowns(cooldowns);
        DocumentCache.update(user, UserDocument.class);
        return cooldown;
    }

    @Override
    public void purge(User user) {
        DocumentCache.get(user, UserDocument.class).setCooldownsRaw(UserDocument.DEFAULT_COOLDOWNS);
        DocumentCache.update(user, UserDocument.class);
    }
}
