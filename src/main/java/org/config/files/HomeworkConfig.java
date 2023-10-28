package org.config.files;

import org.config.ConfigFile;

import static org.time.Time.getDateString;
import static org.values.Global.*;

public class HomeworkConfig extends ConfigFile {
    public HomeworkConfig(String fileName) {
        super(fileName);
    }

    private String getHwCode(String line) {
        return line.split(dateSeperator)[0];
    }

    private String getHwDate(String line) {
        return line.split(dateSeperator)[1].split(keySeperator)[0];
    }

    private String getHwValue(String line) {
        String[] split = line.split(keySeperator);
        if (split.length < 2) {
            return null;
        }
        return line.split(keySeperator)[1];
    }

    private String getHwLineFromCode(String subjCode) {
        for (String line : getLines()) {
            if (getHwCode(line).equals(subjCode)) {
                return line;
            }
        }
        return null;
    }

    public boolean setHomework(String subjCode, String hw) {
        if (hw == null) hw = "";
        return setKey(subjCode + dateSeperator + getDateString(), hw);
    }

    public void resetHomework(String subjCode) {
        setHomework(subjCode, null);
    }

    public String getHomework(String subjCode) {
        String hwLine = getHwLineFromCode(subjCode);
        if (hwLine == null) {
            return null;
        }
        return getHwValue(hwLine);
    }

    public String getHomeworkDate(String subjCode) {
        String hwLine = getHwLineFromCode(subjCode);
        if (hwLine == null) {
            return null;
        }
        return getHwDate(hwLine);
    }
}
