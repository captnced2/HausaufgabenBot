package org.config.files;

import org.config.ConfigFile;

import java.io.*;

import static org.time.Time.getDate;
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
        try {
            String[] lines = getLines();
            BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigFilePath()));
            boolean newHw = true;
            for (String line : lines) {
                if (getHwCode(line).equals(subjCode)) {
                    line = subjCode + dateSeperator + getDate() + keySeperator + hw;
                    newHw = false;
                }
                writer.write(line + newLine);
            }
            if (newHw) {
                writer.write(subjCode + dateSeperator + getDate() + keySeperator + hw + newLine);
            }
            writer.close();
            return newHw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
