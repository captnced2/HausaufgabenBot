package org.jda.slashcommands;

public enum JdaPermission {
    OWNER(4),
    ADMIN(3),
    USER(0);

    private final int level;

    JdaPermission(int permissionLevel) {
        level = permissionLevel;
    }

    public int getAsInt() {
        return level;
    }
}
