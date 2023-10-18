package org.jda.slashcommands;

public enum JdaPermission {
    OWNER(4),
    ADMIN(3),
    MOD(2),
    USER(0);

    private final int level;

    JdaPermission(int permissionLevel) {
        level = permissionLevel;
    }

    public int getAsInt() {
        return level;
    }

    public static JdaPermission getFromInt(int permissionLevel) {
        for (JdaPermission permission : JdaPermission.values()) {
            if (permission.getAsInt() == permissionLevel) {
                return permission;
            }
        }
        return USER;
    }
}
