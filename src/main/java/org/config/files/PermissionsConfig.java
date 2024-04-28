package org.config.files;

import org.config.ConfigFile;
import org.jda.slashcommands.JdaPermission;

import java.util.ArrayList;

import static org.values.Global.keySeperator;

public class PermissionsConfig extends ConfigFile {
    public PermissionsConfig(String fileName) {
        super(fileName);
    }

    public JdaPermission getPermissionsById(String id) {
        String permissions = getKey(id, true);
        if (permissions == null) {
            return JdaPermission.getFromInt(0);
        }
        return JdaPermission.getFromInt(Character.getNumericValue(permissions.charAt(0)));
    }

    @SuppressWarnings("unused")
    public String[] getAllIdsWithPermission(int level) {
        String[] lines = getLines();
        ArrayList<String> ids = new ArrayList<>();
        for (String line : lines) {
            if (Character.getNumericValue(line.split(keySeperator)[1].charAt(0)) >= level) {
                ids.add(line.split(keySeperator)[0]);
            }
        }
        String[] out = new String[ids.size()];
        return ids.toArray(out);
    }
}
