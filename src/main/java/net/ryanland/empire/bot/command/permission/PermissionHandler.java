package net.ryanland.empire.bot.command.permission;

import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PermissionHandler {

    private static HashMap<Member, Permission> MEMBER_PERMISSIONS = new HashMap<>();

    public static Permission getPermission(String id) {
        return Permission.PERMISSIONS.get(id);
    }

    public static Permission getHighestPermission(Member member) {
        Permission memberPermission = MEMBER_PERMISSIONS.get(member);
        if (memberPermission != null) return memberPermission;

        List<Permission> reversedPermissions = Arrays.stream(Permission.values()).toList();
        Collections.reverse(reversedPermissions);

        for (Permission permission : reversedPermissions) {
            if (permission.checkPermission(member)) {
                MEMBER_PERMISSIONS.put(member, permission);
                return permission;
            }
        }

        return null;
    }

    public static void removePermissionCache(Member member) {
        MEMBER_PERMISSIONS.remove(member);
    }

    public static void purgePermissionCache() {
        MEMBER_PERMISSIONS.clear();
    }
}
