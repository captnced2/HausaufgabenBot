package org.config.files;

import org.config.ConfigFile;
import org.time.Weekday;

import java.io.File;

import static org.main.Variables.pfpsFolder;
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

    public String getPermissionsFile() {
        return getKey(permissonsConfigKey) + configExtension;
    }

    public String getSubjectsFile() {
        return getKey(subjectsConfigKey) + configExtension;
    }

    public String getHomeworkFile() {
        return getKey(homeworkConfigKey) + configExtension;
    }

    public String getCancelledSubjectsFile() {
        return getKey(cancelledConfigKey) + configExtension;
    }

    public File getStandardProfilePictureFile() {
        return new File(pfpsFolder + "\\" + getKey(standardProfilePictureKey) + jpgExtension);
    }
}
