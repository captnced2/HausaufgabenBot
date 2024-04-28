package org.main;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.*;
import org.config.files.subjects.SubjectsConfig;
import org.config.files.timetable.TimetableConfig;
import org.values.Global;

import java.nio.file.FileSystems;

public class Variables {
    public static String mainConfPath;
    public static MainConfig mainConfig;
    public static PermissionsConfig permissionsConfig;
    public static SubjectsConfig subjsConfig;
    public static HomeworkConfig homeworkConfig;
    public static HolidayConfig holidayConfig;
    public static TimetableConfig timetableConfig;
    public static String logFile;
    public static String pfpsFolder;
    public static OptionData subjOption;
    public static OptionData dateOption;
    public static int commandsCount;
    public static String slash;

    public static void initVariables() {
        slash = FileSystems.getDefault().getSeparator();
        mainConfPath = "." + slash + Global.configFolder + slash;
        logFile = Global.logFileName;
        pfpsFolder = mainConfPath + Global.pfpsPath;
    }
}
