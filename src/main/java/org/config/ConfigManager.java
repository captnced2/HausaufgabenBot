package org.config;

import org.config.files.*;
import org.values.Global;

import java.io.*;

import static org.main.Variables.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;

public class ConfigManager {

    static void writeConfigTemplate(String filePath, String template) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(template);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToLog(String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(line + newLine);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initConfigs() {
        checkFiles();
        mainConfig = new MainConfig(configFileName);
        homeworkConfig = new HomeworkConfig(mainConfig.getHomeworkFile());
        userConfig = new UserConfig(mainConfig.getUsersFile());
    }

    private void checkFiles() {
        File configFolder = new File(Global.configFolder);
        File pfpFolder = new File(pfpsFolder);
        String mainConfigPath = mainConfPath + configFileName;
        createFolder(configFolder);
        createFolder(pfpFolder);
        if (!new File(mainConfigPath).exists()) {
            new MainConfig(configFileName);
            sendMainConfigCreatedEnterValues();
            System.exit(0);
        }
    }

    private void createFolder(File folder) {
        if (!folder.exists()) {
            sendCreatingNewFolder(folder.getName());
            boolean success = folder.mkdir();
            if (!success) {
                sendCantCreateFolderError(folder.getName());
            }
        }
    }

    @SuppressWarnings("unused")
    private File[] getAllPfps() {
        File path = new File(pfpsFolder);
        if (!path.exists()) {
            return null;
        }
        if (!path.isDirectory()) {
            return null;
        }
        return path.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(jpgExtension));
    }
}
