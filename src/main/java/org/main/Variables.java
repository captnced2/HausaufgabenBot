package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.*;
import org.values.Global;

import java.io.File;

public class Variables {
    public static String mainConfPath;
    public static MainConfig mainConfig;
    public static PermissionsConfig permissionsConfig;
    public static SubjectsConfig subjsConfig;
    public static HomeworkConfig homeworkConfig;
    public static CancelledSubjectsConfig cancelledConfig;
    public static String logFile;
    public static String pfpsFolder;
    public static File[] pfps;
    public static String alreadyPosted;
    public static OptionData subjOption;
    public static int commandsCount;

    public static void initVariables() {
        mainConfPath = ".\\" + Global.configFolder + "\\";
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
        alreadyPosted = "";
    }
}
