package net.ryanland.empire.sys.ranks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jdi.InvalidTypeException;
import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.permission.Permission;
import net.ryanland.empire.bot.command.permission.PermissionHandler;
import net.ryanland.empire.sys.config.Config;
import net.ryanland.empire.sys.externalfiles.ExternalFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class RankHandler {

    private static final HashMap<Permission, List<String>> RANKED_USERS = new HashMap<>();

    public static void loadRanks() throws IOException {
        JsonObject json = ExternalFiles.RANKS.parseJson();

        for (String key : json.keySet()) {
            Permission permission = PermissionHandler.getPermission(key);
            if (permission == null) throw new NoSuchElementException();

            JsonArray array = json.getAsJsonArray(key);
            List<String> users = new ArrayList<>();
            for (JsonElement element : array) {
                users.add(element.getAsString());
            }
            RANKED_USERS.put(permission, users);
        }
    }

    public List<String> getRankUsers(Permission permission) {
        return RANKED_USERS.get(permission);
    }

    public static boolean hasRank(User user, Permission permission) {
        return RANKED_USERS.get(permission).contains(user.getId());
    }
}
