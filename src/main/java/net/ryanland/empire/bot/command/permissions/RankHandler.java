package net.ryanland.empire.bot.command.permissions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.command.permissions.PermissionHandler;
import net.ryanland.empire.sys.file.local.LocalFiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class RankHandler {

    private static final HashMap<Long, Permission> USER_RANKS = new HashMap<>();

    public static void loadRanks() throws IOException {
        JsonObject json = LocalFiles.RANKS.parseJson();

        for (String key : json.keySet()) {
            Permission permission = PermissionHandler.getPermission(key);
            if (permission == null) throw new NoSuchElementException();

            JsonArray array = json.getAsJsonArray(key);
            for (JsonElement element : array) {
                USER_RANKS.put(element.getAsLong(), permission);
            }
        }
    }

    public static boolean hasRank(User user, Permission permission) {
        Permission perm = USER_RANKS.get(user.getIdLong());
        if (perm == null) return false;

        return perm.getLevel() >= permission.getLevel();
    }
}
