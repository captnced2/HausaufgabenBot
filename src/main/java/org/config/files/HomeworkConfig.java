package org.config.files;

import org.config.ConfigFile;
import org.config.files.subjects.Subject;

import java.util.Date;

import static org.time.Time.*;
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

    private String getHwLineFromCode(Subject subject) {
        for (String line : getLines()) {
            if (getHwCode(line).equals(subject.code())) {
                return line;
            }
        }
        return null;
    }

    public boolean setHomework(Subject subject, String hw) {
        if (hw == null) hw = "";
        return setKeyWithSeperator(subject.code(), getDateString() + keySeperator + hw, dateSeperator, true);
    }

    public void resetHomework(Subject subject) {
        setHomework(subject, null);
    }

    public void resetHomeworkIfOld(Subject subject) {
        Date date = getHomeworkDate(subject);
        if (date.toInstant().isBefore(getDate().toInstant())) {
            resetHomework(subject);
        }
    }

    public String getHomework(Subject subject) {
        String hwLine = getHwLineFromCode(subject);
        if (hwLine == null) {
            return null;
        }
        return getHwValue(hwLine);
    }

    public Date getHomeworkDate(Subject subject) {
        String hwLine = getHwLineFromCode(subject);
        if (hwLine == null) {
            return null;
        }
        return getDateFromString(getHwDate(hwLine));
    }
}
