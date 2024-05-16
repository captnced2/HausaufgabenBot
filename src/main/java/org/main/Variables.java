package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.*;
import org.config.files.subjects.SubjectsConfig;
import org.config.files.timetable.TimetableConfig;
import org.values.Global;

public class Variables {
    public static String mainConfPath;
    public static MainConfig mainConfig;
    public static PermissionsConfig permissionsConfig;
    public static SubjectsConfig subjsConfig;
    public static HomeworkConfig homeworkConfig;
    public static TimetableConfig timetableConfig;
    public static String logFile;
    public static String pfpsFolder;
    public static OptionData subjOption;
    public static OptionData dateOption;
    public static int commandsCount;

    public static void initVariables() {
        mainConfPath = "./" + Global.configFolder + "/";
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
    }
}
