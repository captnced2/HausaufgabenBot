package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.values.Global;

import java.io.File;

public class Variables {
    public static String mainConfPath;
    public static String configFolderPath;
    public static String tokenConf;
    public static String subjsConf;
    public static String permissionsConf;
    public static String homeworkConf;
    public static String timetableConf;
    public static String cancelledConf;
    public static String idsConf;
    public static String logFile;
    public static String pfpsFolder;
    public static File[] pfps;
    public static String token;
    public static String[] subjs;
    public static String pingRole;
    public static String classServerId;
    public static String homeworkChannelId;
    public static String lukasID;
    public static String alreadyPosted;
    public static OptionData subjOption;
    public static int commandsCount;

    public static void initVariables() {
        mainConfPath = Global.mainConfigPath;
        configFolderPath = mainConfPath + Global.configPath;
        tokenConf = mainConfPath + Global.tokenConfigFileName;
        subjsConf = mainConfPath + Global.subjsConfigFileName;
        permissionsConf = mainConfPath + Global.permissionsConfigFileName;
        homeworkConf = mainConfPath + Global.homeworkConfigFileName;
        timetableConf = mainConfPath + Global.timetableConfigFileName;
        cancelledConf = mainConfPath + Global.cancelledSubjectsConfigFileName;
        idsConf = mainConfPath + Global.idsConfigFileName;
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
        alreadyPosted = "";
    }
}
