package net.ryanland.empire.bot.command.permissions;

import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PermissionHandler {

    public static final HashMap<String, Permission> PERMISSIONS = new HashMap<>();
    private static final HashMap<Member, Permission> MEMBER_PERMISSIONS = new HashMap<>();

    public static void loadPermissions() {
        for (Permission permission : Permission.values()) {
            PermissionHandler.PERMISSIONS.put(permission.getId(), permission);
        }
    }

    public static Permission getPermission(String id) {
        return PERMISSIONS.get(id);
    }

    public static Permission getHighestPermission(Member member) {
        Permission memberPermission = MEMBER_PERMISSIONS.get(member);
        if (memberPermission != null) return memberPermission;

        List<Permission> reversedPermissions = Arrays.asList(Permission.values());
        Collections.reverse(reversedPermissions);

        for (Permission permission : reversedPermissions) {
            if (permission.checkPermission(member)) {
                MEMBER_PERMISSIONS.put(member, permission);
                return permission;
            }
        }

        throw new IllegalStateException();
    }

    public static void removePermissionCache(Member member) {
        MEMBER_PERMISSIONS.remove(member);
    }

    public static void purgePermissionCache() {
        MEMBER_PERMISSIONS.clear();
    }
}
