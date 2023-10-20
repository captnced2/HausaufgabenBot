package org.values;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Global {

    public static final String version = "4.1.2";
    public static final String mainConfigPath = ".\\config\\";
    public static final String configPath = "config";
    public static final String pfpsPath = "pfp";
    public static final String tokenConfigFileName = "Token.conf";
    public static final String subjsConfigFileName = "Fach.conf";
    public static final String permissionsConfigFileName = "Rechte.conf";
    public static final String homeworkConfigFileName = "Hausaufgaben.conf";
    public static final String timetableConfigFileName = "Stundenplan.conf";
    public static final String cancelledSubjectsConfigFileName = "Entfall.conf";
    public static final String idsConfigFileName = "IDs.conf";
    public static final String logFileName = "Hausi-Bot.log";
    public static final String subjsRegex = ": ";
    public static final String commaRegex = ", ";
    public static final String commentSymbol = "#";
    public static final Color homeworkEmbedColor = Color.YELLOW;
    public static final Color errorEmbedColor = Color.RED;
    public static final Color successEmbedColor = Color.GREEN;
    public static final String embedFooterSeperator = " • ";
    public static final String newLine = "\n";
    public static final String doubleQuotesString = "\"";
    public static final String commandPackageName = "org.jda.slashcommands.commands";
    public static final long oneDayInMilliseconds = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
    public static final String hoursMinutesPattern = "HH:mm";
    public static final String dayMonthYearPattern = "dd.MM.yyyy";
    public static final String weekdayDayMonthYearHoursMinutesPattern = "EEEE dd.MM.yyyy HH:mm";
    public static final String jpgEnd = ".jpg";
}
