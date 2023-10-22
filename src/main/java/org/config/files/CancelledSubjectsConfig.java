package org.config.files;

import org.config.ConfigFile;

import java.io.*;

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
        try {
            String[] lines = getLines();
            BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigFilePath()));
            boolean isNew = true;
            for (String line : lines) {
                if (getCancelledDate(line).equals(date)) {
                    line = line + commaRegex + subjCode;
                    isNew = false;
                }
                writer.write(line + newLine);
            }
            if (isNew) {
                writer.write(date + keySeperator + subjCode + newLine);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
