package org.config;

import org.time.Weekday;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.main.Varibles.*;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;

public class Config {

    public static void initConfigs() {
        checkFiles();
        getToken();
        getSubjs();
        getPfps();
        getIds();
    }

    public static void initCache() {
        logCache = new ArrayList<>();
    }

    public static void checkFiles() {
        File pfp = new File(pfpsFolder);
        File[] confgs = {new File(tokenConf), new File(subjsConf), new File(permissionsConf), new File(homeworkConf), new File(timetableConf), new File(cancelledConf), new File(idsConf), new File(logFile)};
        for (File c : confgs) {
            if (!c.exists()) {
                sendCreatingNewFile(c.getName());
                try {
                    boolean success;
                    success = c.createNewFile();
                    if (!success) {
                        sendCantCreateConfError(c.getName());
                    }
                } catch (IOException ignored) {
                }
            }
        }
        if (!pfp.exists()) {
            sendFolderNotFound(pfp.getName());
            try {
                boolean success;
                success = pfp.createNewFile();
                if (!success) {
                    sendCantCreateFolderError(pfp.getName());
                }
            } catch (IOException ignored) {
            }
        }
        writeLogCache();
    }

    private static void getToken() {
        token = getFile(tokenConf)[0];
    }

    private static void getSubjs() {
        subjs = getFile(subjsConf);
    }

    private static void getIds() {
        String[] idList = getFile(idsConf);
        classServerId = idList[0].split(subjsRegex)[1];
        homeworkChannelId = idList[1].split(subjsRegex)[1];
        pingRole = "<@&" + idList[2].split(subjsRegex)[1] + ">";
        lukasID = idList[3].split(subjsRegex)[1];
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
        String[] permissionsFile = getFile(permissionsConf);
        for (String permission : permissionsFile) {
            if (permission.split(subjsRegex)[1].equals(id)) {
                try {
                    return Integer.parseInt(permission.split(subjsRegex)[2]);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return 0;
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

    public static String[] getDaySubj(Weekday day) {
        String[] stp = getFile(timetableConf);
        String[] split;
        for (String s : stp) {
            split = s.split(subjsRegex);
            if (split[0].equals(day.getAsString())) {
                return split[1].split(commaRegex);
            }
        }
        return null;
    }

    public static String[] getPendingDelSubj() {
        ArrayList<String> pendingDel = new ArrayList<>();
        String[] daySubjs = getDaySubj(getWeekday());
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
        if (!new File(logFile).exists()) {
            logCache.add(line);
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(line + newLine);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeLogCache() {
        if (logCache.isEmpty()) {
            logCache = null;
            return;
        }
        for (String line : logCache) {
            writeToLog(line);
        }
        logCache = null;
    }

    private static String[] getFile(String conf) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(conf), StandardCharsets.UTF_8));
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
