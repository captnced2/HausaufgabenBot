package org.config;

import org.config.files.*;
import org.values.Global;

import java.io.*;
import java.util.ArrayList;

import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;

public class Config {

    public static void initConfigs() {
        checkFiles();
        mainConfig = new MainConfig(configFileName);
        permissionsConfig = new PermissionsConfig(mainConfig.getPermissionsFile());
        subjsConfig = new SubjectsConfig(mainConfig.getSubjectsFile());
        homeworkConfig = new HomeworkConfig(mainConfig.getHomeworkFile());
        cancelledConfig = new CancelledSubjectsConfig(mainConfig.getCancelledSubjectsFile());
    }

    public static void checkFiles() {
        File configFolder = new File(Global.configFolder);
        File pfpFolder = new File(pfpsFolder);
        String mainConfigPath = mainConfPath + configFileName;
        createFolder(configFolder);
        createFolder(pfpFolder);
        if (!new File(mainConfigPath).exists()) {
            writeConfigTemplate(mainConfigPath, mainConfigTemplate);
            sendMainConfigCreatedEnterValues();
            System.exit(0);
        }
    }

    public static void createFolder(File folder) {
        if (!folder.exists()) {
            sendCreatingNewFolder(folder.getName());
            boolean success = folder.mkdir();
            if (!success) {
                sendCantCreateFolderError(folder.getName());
            }
        }
    }

    public static void createFile(File file) {
        if (!file.exists()) {
            sendCreatingNewFile(file.getName());
            try {
                boolean success = file.createNewFile();
                if (!success) {
                    sendCantCreateConfError(file.getName());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void writeConfigTemplate(String filePath, String template) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(template);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getPendingDelSubj() {
        ArrayList<String> pendingDel = new ArrayList<>();
        String[] daySubjs = mainConfig.getSubjsOnDay(getWeekday());
        if (daySubjs == null) {
            return null;
        }
        for (String subj : daySubjs) {
            String hwDate = homeworkConfig.getHomeworkDate(subj);
            String hw = homeworkConfig.getHomework(subj);
            String[] cncldHw = cancelledConfig.getCancelledSubjs(getDate());
            if (hwDate != null && hw != null) {
                if (!hwDate.equals(getDate()) && !hwDate.isEmpty() && !hw.isEmpty()) {
                    if (cncldHw == null) {
                        pendingDel.add(subj);
                    } else {
                        boolean isCncld = false;
                        for (String s : cncldHw) {
                            if (s.equals(subj)) {
                                isCncld = true;
                                break;
                            }
                        }
                        if (!isCncld) {
                            pendingDel.add(subj);
                        }
                    }
                }
            }
        }
        if (pendingDel.isEmpty()) {
            return null;
        }
        String[] out = new String[pendingDel.size()];
        return pendingDel.toArray(out);
    }

    private static File[] getAllPfps() {
        File path = new File(pfpsFolder);
        if (!path.exists()) {
            return null;
        }
        if (!path.isDirectory()) {
            return null;
        }
        return path.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(jpgExtension));
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
}
