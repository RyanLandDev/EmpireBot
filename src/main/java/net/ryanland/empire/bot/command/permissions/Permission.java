package net.ryanland.empire.bot.command.permissions;

import net.dv8tion.jda.api.entities.Member;
import net.ryanland.empire.sys.ranks.RankHandler;

import java.util.HashMap;

public enum Permission {
    USER("user"){
        @Override
        public boolean checkPermission(Member member) {
            return true;
        }
    },
    // -------------------------------------------------------------------
    SERVER_ADMIN("serverAdmin") {
        @Override
        public boolean checkPermission(Member member) {
            return member.hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR);
        }
    },
    // -------------------------------------------------------------------
    DEVELOPER("developer") {
        @Override
        public boolean checkPermission(Member member) {
            return RankHandler.hasRank(member.getUser(), this);
        }
    },
    OWNER("owner") {
        @Override
        public boolean checkPermission(Member member) {
            return RankHandler.hasRank(member.getUser(), this);
        }
    };

    private final String id;
    private final int level;

    Permission(String id) {
        this.id = id;
        this.level = ordinal();
        putPermission();
    }

    private void putPermission() {
        PermissionHandler.PERMISSIONS.put(getId(), this);
    }

    public abstract boolean checkPermission(Member member);

    public boolean hasPermission(Member member) {
        return PermissionHandler.getHighestPermission(member).getLevel() >= getLevel();
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
