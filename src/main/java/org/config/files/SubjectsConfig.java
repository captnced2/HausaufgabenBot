package org.config.files;

import org.config.ConfigFile;

import java.util.ArrayList;

import static org.values.Global.keySeperator;
import static org.values.strings.Errors.noSubjectNameError;

public class SubjectsConfig extends ConfigFile {
    public SubjectsConfig(String fileName) {
        super(fileName);
    }

    public String[] getAllCodes() {
        String[] lines = getLines();
        ArrayList<String> codes = new ArrayList<>();
        for (String line : lines) {
            codes.add(line.split(keySeperator)[1]);
        }
        String[] out = new String[codes.size()];
        return codes.toArray(out);
    }

    public String getNameFromCode(String code) {
        String[] lines = getLines();
        for (String line : lines) {
            if (line.split(keySeperator)[1].equals(code)) {
                return line.split(keySeperator)[0];
            }
        }
        throw new RuntimeException(noSubjectNameError(code));
    }

    public String[] getRaw() {
        return getLines();
    }
}
