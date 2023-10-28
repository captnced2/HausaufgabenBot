package org.config.files;

import org.config.ConfigFile;

import static org.values.Global.*;

public class CancelledSubjectsConfig extends ConfigFile {
    public CancelledSubjectsConfig(String fileName) {
        super(fileName);
    }

    private String getCancelledDate(String line) {
        return line.split(keySeperator)[0];
    }

    private String[] getAllCancelledSubjs(String line) {
        return line.split(keySeperator)[1].split(commaRegex);
    }

    public void addCancelledSubj(String subjCode, String date) {
        setKey(date, subjCode, false);
    }

    public String[] getCancelledSubjs(String date) {
        String[] lines = getLines();
        for (String line : lines) {
            if (getCancelledDate(line).equals(date)) {
                return getAllCancelledSubjs(line);
            }
        }
        return null;
    }
}
