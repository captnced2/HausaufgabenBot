package org.config.files;

import org.config.ConfigFile;

import java.io.File;

import static org.main.Variables.pfpsFolder;
import static org.values.Global.*;

public class MainConfig extends ConfigFile {

    public MainConfig(String fileName) {
        super(fileName);
    }

    @Override
    protected String getTemplate() {
        return """
                # Discord Bot Token
                # token=s
                
                # Users Datei
                # users=
                
                # Hausaufgaben Datei
                # homework=
                
                # Standard Profilbild Datei im pfp Ordner
                # standardProfilePicture=
                """;
    }

    public String getToken() {
        return getKey(tokenKey);
    }

    private String getConfigFileByKey(String key) {
        return getKey(key) + configExtension;
    }

    public String getHomeworkFile() {
        return getConfigFileByKey(homeworkConfigKey);
    }

    public String getUsersFile() {
        return getConfigFileByKey(usersConfigKey);
    }

    public File getStandardProfilePictureFile() {
        return new File(pfpsFolder + "/" + getKey(standardProfilePictureKey) + jpgExtension);
    }
}
