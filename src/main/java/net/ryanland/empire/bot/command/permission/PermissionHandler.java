package net.ryanland.empire.bot.command.permission;

public class PermissionHandler {

    public static Permission getPermission(String id) {
        return Permission.PERMISSIONS.get(id);
    }
}
