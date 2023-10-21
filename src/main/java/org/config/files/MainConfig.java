package org.config.files;

import org.time.Weekday;

import static org.values.Global.*;

public class MainConfig extends ConfigFile {

    public MainConfig(String file) {
        super(file);
    }

    public String getToken() {
        return getKey(tokenKey);
    }

    public String[] getSubjsOnDay(Weekday day) {
        return getKey(day.getAsString()).split(commaRegex);
    }
}
