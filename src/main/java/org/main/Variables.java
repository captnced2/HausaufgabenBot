package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.MainConfig;
import org.values.Global;

import java.io.File;

public class Variables {
    public static String mainConfPath;
    public static String mainConfFile;
    public static MainConfig mainConfig;
    public static String subjsConf;
    public static String permissionsConf;
    public static String homeworkConf;
    public static String cancelledConf;
    public static String logFile;
    public static String pfpsFolder;
    public static File[] pfps;
    public static String[] subjs;
    public static String alreadyPosted;
    public static OptionData subjOption;
    public static int commandsCount;

    public static void initVariables() {
        mainConfPath = ".\\" + Global.configFolder + "\\";
        mainConfFile = mainConfPath + Global.configFileName;
        subjsConf = mainConfPath + Global.subjsConfigFileName;
        permissionsConf = mainConfPath + Global.permissionsConfigFileName;
        homeworkConf = mainConfPath + Global.homeworkConfigFileName;
        cancelledConf = mainConfPath + Global.cancelledSubjectsConfigFileName;
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
        alreadyPosted = "";
    }
}
