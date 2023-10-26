package org.values;

import java.awt.*;

public class Global {
    public static final String version = "5.5.1";
    public static final String configFolder = "configs";
    public static final String configFileName = "config.conf";
    public static final String pfpsPath = "pfp";
    public static final String logFileName = "Hausi-Bot.log";
    public static final String subjsRegex = ": ";
    public static final String commaRegex = ", ";
    public static final String commentSymbol = "#";
    public static final String keySeperator = "=";
    public static final String dateSeperator = "-";
    public static final Color homeworkEmbedColor = Color.YELLOW;
    public static final Color errorEmbedColor = Color.RED;
    public static final Color successEmbedColor = Color.GREEN;
    public static final Color logEmbedColor = Color.BLUE;
    public static final String embedFooterSeperator = " • ";
    public static final String newLine = "\n";
    public static final String doubleQuotesString = "\"";
    public static final String commandPackageName = "org.jda.slashcommands.commands";
    public static final String hoursMinutesPattern = "HH:mm";
    public static final String dayMonthYearPattern = "dd.MM.yyyy";
    public static final String weekdayDayMonthYearHoursMinutesPattern = "EEEE dd.MM.yyyy HH:mm";
    public static final String jpgExtension = ".jpg";
    public static final String configExtension = ".conf";
    public static final String homeworkChannel = "hausaufgaben";
    public static final String homeworkLogChannel = "hausaufgaben-log";
    public static final String tokenKey = "token";
    public static final String standardProfilePictureKey = "standardProfilePicture";
    public static final String permissonsConfigKey = "permissions";
    public static final String subjectsConfigKey = "subjects";
    public static final String homeworkConfigKey = "homework";
    public static final String cancelledConfigKey = "cancelledSubjects";
    public static final String pingRoleName = "Hausaufgaben Ping";
    public static final String lukasIDKey = "LukasID";
    public static final String mainConfigTemplate = """
            # Discord Bot Token
            # token=

            # Stundenplan
            # {WOCHENTAG}={CODE}, {CODE}, {...}

            # Rechte Datei
            # permissions=

            # Fächer Datei
            # subjects=

            # Hausaufgaben Datei
            # homework=

            # Entfallene Fächer Datei
            # cancelledSubjects=

            # Standart Profilbild Datei im pfp Ordner
            # standardProfilePicture=

            # Ids""";
}
