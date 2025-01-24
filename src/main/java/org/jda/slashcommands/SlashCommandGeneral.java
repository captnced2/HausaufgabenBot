package org.jda.slashcommands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.records.*;
import org.jda.JdaMain;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static org.config.files.webuntis.WebUntisAPI.*;
import static org.main.Variables.*;
import static org.values.Global.*;
import static org.values.strings.Messages.noHomeworkString;

public class SlashCommandGeneral {
    public static final String OptionSubjName = "fach";
    public static final String OptionSubjDescription = "Fach der Hausaufgabe";
    public static final String OptionHomeworkName = "hausi";
    public static final String OptionHomeworkDescription = "Hausaufgabe";
    public static final String OptionDateName = "datum";
    public static final String OptionDateDescription = "Datum";
    public static final String OptionPermissionName = "rechte";
    public static final String OptionPermissionDescription = "Rechtelevel";
    public static final String OptionUserName = "user";
    public static final String OptionUserDescription = "User";

    public static String getHomeworkToDayByUser(Date date, User user) {
        return getHomeworkFromSubjs(userConfig.filterUserSubjects(getSubjsOnDate(date), user), null);
    }

    public static String getHomeworkFromDay(Date date) {
        return getHomeworkFromSubjs(getAllSubjects(), date);
    }

    public static List<OptionData> buildOptionData(OptionData... optionData) {
        ArrayList<OptionData> returnData = new ArrayList<>();
        Collections.addAll(returnData, optionData);
        return returnData;
    }

    private static String getHomeworkFromSubjs(Subject[] subjects, @Nullable Date onDate) {
        if (subjects == null || subjects.length == 0) {
            return noHomeworkString;
        }
        StringBuilder txt = new StringBuilder();
        for (Subject subj : subjects) {
            HomeworkInstance instance = homeworkConfig.getHomework(subj);
            String hw = instance.value();
            Date date = instance.date();
            User user = JdaMain.getUserFromId(instance.userId());
            if (hw != null && !hw.isEmpty() && date != null) {
                if (onDate == null) {
                    if (user != null) {
                        txt.append("(").append(JdaMain.getUserFromId(instance.userId()).getEffectiveName()).append(") ");
                    }
                    txt.append(subj.name()).append(subjsRegex).append(hw).append(newLine);
                } else if (date.equals(onDate)) {
                    txt.append(subj.name());
                    if (user != null) {
                        txt.append("(").append(JdaMain.getUserFromId(instance.userId()).getEffectiveName()).append(") ");
                    }
                    txt.append(subj.name()).append(subjsRegex).append(hw).append(newLine);
                }
            }
        }
        String out = txt.toString();
        if (out.isEmpty()) {
            return noHomeworkString;
        } else {
            return txt.toString();
        }
    }
}
