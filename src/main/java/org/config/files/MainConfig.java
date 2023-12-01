package org.config.files;

import org.config.ConfigFile;
import org.main.Variables;
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

    private String getConfigFileByKey(String key) {
        return getKey(key) + configExtension;
    }

    public String getPermissionsFile() {
        return getConfigFileByKey(permissonsConfigKey);
    }

    public String getSubjectsFile() {
        return getConfigFileByKey(subjectsConfigKey);
    }

    public String getHomeworkFile() {
        return getConfigFileByKey(homeworkConfigKey);
    }

    public String getCancelledSubjectsFile() {
        return getConfigFileByKey(cancelledConfigKey);
    }

    public String getHolydayFile() {
        return getConfigFileByKey(holydayConfigKey);
    }

    public File getStandardProfilePictureFile() {
        return new File(pfpsFolder + Variables.slash + getKey(standardProfilePictureKey) + jpgExtension);
    }
}
