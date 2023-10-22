package org.jda.slashcommands;

import org.jetbrains.annotations.Nullable;
import org.time.Weekday;

import static org.main.Variables.*;
import static org.values.Global.*;

public class SlashCommandGeneral {
    public static final String AcceptDelName = "accept";
    public static final String OptionSubjName = "fach";
    public static final String OptionSubjDescription = "Fach der Hausaufgabe";
    public static final String OptionHomeworkName = "hausi";
    public static final String OptionHomeworkDescription = "Hausaufgabe";
    public static final String OptionDateName = "datum";
    public static final String OptionDateDescription = "Datum";
    public static final String ChoiceAllName = "Alle";
    public static final String ChoiceAllValue = "all";

    public static String getHomeworkToDay(Weekday day) {
        return getHomeworkFromCodes(mainConfig.getSubjsOnDay(day), null);
    }

    public static String getHomeworkFromDay(String date) {
        return getHomeworkFromCodes(subjsConfig.getAllCodes(), date);
    }

    private static String getHomeworkFromCodes(String[] subjCodes, @Nullable String onDate) {
        if (subjCodes == null) {
            return null;
        }
        StringBuilder txt = new StringBuilder();
        for (String subjCode : subjCodes) {
            String hw = homeworkConfig.getHomework(subjCode);
            String date = homeworkConfig.getHomeworkDate(subjCode);
            if (hw != null && !hw.isEmpty() && date != null) {
                if (onDate == null) {
                    txt.append(subjsConfig.getNameFromCode(subjCode)).append(subjsRegex).append(hw).append(newLine);
                } else {
                    if (date.equals(onDate)) {
                        txt.append(subjsConfig.getNameFromCode(subjCode)).append(subjsRegex).append(hw).append(newLine);
                    }
                }
            }
        }
        return txt.toString();
    }
}
