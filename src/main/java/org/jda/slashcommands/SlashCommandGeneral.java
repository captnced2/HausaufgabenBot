package org.jda.slashcommands;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.Nullable;
import org.time.Weekday;

import java.util.*;

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

    public static String getHomeworkToDay(Weekday day, String dateForCancelledSubjs) {
        String[] subjsOnDay = mainConfig.getSubjsOnDay(day);
        String[] subjsCancelledOnDate = cancelledConfig.getCancelledSubjs(dateForCancelledSubjs);
        if (subjsCancelledOnDate == null) {
            return getHomeworkFromCodes(subjsOnDay, null);
        }
        ArrayList<String> finalSubjs = new ArrayList<>();
        for (String subjCode : subjsOnDay) {
            boolean cancelled = false;
            for (String subjCancelled : subjsCancelledOnDate) {
                if (subjCode.equals(subjCancelled)) {
                    cancelled = true;
                    break;
                }
            }
            if (!cancelled) {
                finalSubjs.add(subjCode);
            }
        }
        return getHomeworkFromCodes(finalSubjs.toArray(new String[0]), null);
    }

    public static String getHomeworkFromDay(String date) {
        return getHomeworkFromCodes(subjsConfig.getAllCodes(), date);
    }

    public static List<OptionData> buildOptionData(OptionData firstOption) {
        ArrayList<OptionData> optionData = new ArrayList<>();
        optionData.add(firstOption);
        return optionData;
    }

    public static List<OptionData> buildOptionData(OptionData firstOption, OptionData secondOption) {
        ArrayList<OptionData> optionData = new ArrayList<>();
        optionData.add(firstOption);
        optionData.add(secondOption);
        return optionData;
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
