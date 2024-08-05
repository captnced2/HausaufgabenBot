package org.config.files;

import org.config.ConfigFile;

import java.io.File;

import static org.main.Variables.pfpsFolder;
import static org.values.Global.*;

public class MainConfig extends ConfigFile {

    public MainConfig(String fileName) {
        super(fileName);
    }

    public String getToken() {
        return getKey(tokenKey);
    }

    public String getTimetableFile() {
        return getConfigFileByKey(timetableConfigKey);
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

    public File getStandardProfilePictureFile() {
        return new File(pfpsFolder + "/" + getKey(standardProfilePictureKey) + jpgExtension);
    }
}
