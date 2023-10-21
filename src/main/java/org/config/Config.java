package org.config;

import org.config.files.MainConfig;
import org.values.Global;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;

public class Config {

    public static void initConfigs() {
        checkFiles();
        mainConfig = new MainConfig(mainConfFile);
        getSubjs();
        getPfps();
    }

    public static void checkFiles() {
        File configFolder = new File(Global.configFolder);
        File pfpFolder = new File(pfpsFolder);
        File[] confgs = {new File(mainConfFile), new File(subjsConf), new File(permissionsConf), new File(homeworkConf), new File(timetableConf), new File(cancelledConf), new File(idsConf)};
        createFolder(configFolder);
        for (File c : confgs) {
            createFile(c);
        }
        createFolder(pfpFolder);
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

    private static void getSubjs() {
        subjs = getFile(subjsConf);
    }

    public static String[] getAllSubjCodes() {
        StringBuilder codes = new StringBuilder();
        for (String s : subjs) {
            codes.append(s.split(subjsRegex)[1]).append(";");
        }
        return codes.toString().split(";");
    }

    public static String getSubjFromCode(String code) {
        String subjName = null;
        String[] sub;
        for (String s : subjs) {
            sub = s.split(subjsRegex);
            if (sub[1].equals(code)) {
                subjName = sub[0];
            }
        }
        return subjName;
    }

    public static int getPermissionLevelFromId(String id) {
        String[] allPermissions = getAllPermissions();
        for (String permission : allPermissions) {
            if (permission.split(commaRegex)[0].equals(id)) {
                return Integer.parseInt(permission.split(commaRegex)[1]);
            }
        }
        return 0;
    }

    public static String[] getAllPermissions() {
        String[] permissionsFile = getFile(permissionsConf);
        ArrayList<String> allPermissions = new ArrayList<>();
        for (String permission : permissionsFile) {
            allPermissions.add(permission.split(subjsRegex)[1] + commaRegex + permission.split(subjsRegex)[2]);
        }
        String[] out = new String[allPermissions.size()];
        return allPermissions.toArray(out);
    }

    public static boolean setHomework(String subjCode, String ha) {
        try {
            String[] has = getFile(homeworkConf);
            BufferedWriter writer = new BufferedWriter(new FileWriter(homeworkConf));
            boolean isNew = true;
            for (String s : has) {
                String[] line = s.split(subjsRegex);
                if (line[0].equals(subjCode)) {
                    s = subjCode + subjsRegex + getDate() + subjsRegex + ha;
                    isNew = false;
                }
                writer.write(s + newLine);
            }
            if (isNew) {
                writer.write(subjCode + subjsRegex + getDate() + subjsRegex + ha + newLine);
            }
            writer.close();
            return isNew;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetHomework(String subjCode) {
        setHomework(subjCode, "");
    }

    public static String getHomework(String subjCode) {
        String[] has = getFile(homeworkConf);
        for (String s : has) {
            String[] sub = s.split(subjsRegex);
            if (sub[0].equals(subjCode)) {
                if (sub.length < 3) {
                    return "";
                } else {
                    StringBuilder homework = new StringBuilder();
                    if (sub.length > 3) {
                        homework.append(sub[2]);
                        for (int i = 3; i < sub.length; i++) {
                            homework.append(subjsRegex).append(sub[i]);
                        }
                        return homework.toString();
                    } else {
                        return sub[2];
                    }
                }
            }
        }
        return null;
    }

    public static String getHomeworkDate(String subj) {
        String[] date = getFile(homeworkConf);
        String[] sub;
        for (String s : date) {
            sub = s.split(subjsRegex);
            if (sub[0].equals(subj)) {
                return sub[1];
            }
        }
        return null;
    }

    public static String[] getPendingDelSubj() {
        ArrayList<String> pendingDel = new ArrayList<>();
        String[] daySubjs = mainConfig.getSubjsOnDay(getWeekday());
        if (daySubjs == null) {
            return null;
        }
        for (String subj : daySubjs) {
            String hwDate = getHomeworkDate(subj);
            String hw = getHomework(subj);
            String[] cncldHw = getCancelledHw(getDate());
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
        String[] out = new String[pendingDel.size()];
        return pendingDel.toArray(out);
    }

    private static void getPfps() {
        File path = new File(pfpsFolder);
        if (!path.exists()) {
            return;
        }
        if (!path.isDirectory()) {
            return;
        }
        pfps = path.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(jpgEnd));
    }

    public static void addCancelledHw(String subjCode, String date) {
        try {
            String[] cncldHw = getFile(cancelledConf);
            BufferedWriter writer = new BufferedWriter(new FileWriter(cancelledConf));
            boolean isNew = true;
            for (String s : cncldHw) {
                String[] line = s.split(subjsRegex);
                if (line[0].equals(date)) {
                    s = s + commaRegex + subjCode;
                    isNew = false;
                }
                writer.write(s + newLine);
            }
            if (isNew) {
                writer.write(date + subjsRegex + subjCode + newLine);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] getCancelledHw(String date) {
        String[] cncld = getFile(cancelledConf);
        for (String s : cncld) {
            String cncldDate = s.split(subjsRegex)[0];
            if (cncldDate.equals(date)) {
                return s.split(subjsRegex)[1].split(commaRegex);
            }
        }
        return null;
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

    private static String[] getFile(String file) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(commentSymbol)) {
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] arr = new String[lines.size()];
        return lines.toArray(arr);
    }
}
