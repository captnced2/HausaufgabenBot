package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.values.Global;

import java.io.File;
import java.util.ArrayList;

public class Varibles {
    public static String mainConfPath;
    public static String tokenConf;
    public static String subjsConf;
    public static String permissionsConf;
    public static String homeworkConf;
    public static String timetableConf;
    public static String cancelledConf;
    public static String logFile;
    public static String pfpsFolder;
    public static File[] pfps;
    public static String token;
    public static String[] subjs;
    public static String[] admins;
    public static String pingRoleID;
    public static String alreadyPosted;
    public static OptionData subjOption;
    public static int commandsCount;
    public static ArrayList<String> logCache;

    public static void initVariables() {
        mainConfPath = Global.mainConfigPath;
        tokenConf = mainConfPath + Global.tokenConfigFileName;
        subjsConf = mainConfPath + Global.subjsConfigFileName;
        permissionsConf = mainConfPath + Global.permissionsConfigFileName;
        homeworkConf = mainConfPath + Global.homeworkConfigFileName;
        timetableConf = mainConfPath + Global.timetableConfigFileName;
        cancelledConf = mainConfPath + Global.cancelledSubjectsConfigFileName;
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
        pingRoleID = "<@&" + Global.classDiscordHomeworkPingRoleId + ">";
        alreadyPosted = "";
    }
}
