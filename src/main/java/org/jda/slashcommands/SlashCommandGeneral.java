package org.jda.slashcommands;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.JdaMain;
import org.jetbrains.annotations.Nullable;
import org.time.Weekday;

import static org.jda.JdaMain.*;
import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Messages.*;

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

    public static void postMessage(SlashCommandInteractionEvent event) {
        if (isWeekend()) {
            replyEmbed(event, notSchoolday(), true);
            return;
        }
        String homework = getHomeworkFromDay(getDate());
        if (homework.isEmpty()) {
            homework = noHomeworkString;
        }
        if (event == null) {
            TextChannel channel = JdaMain.getTextChannelFromId(mainConfig.getKey(classServerIdKey), mainConfig.getKey(homeworkChannelIdKey));
            if (channel != null) {
                sendEmbed(channel, homeworkFromDay(homework));
                sendWithDelay(channel, mainConfig.getKey(pingRoleIdKey), 1);
            }
        } else {
            replyEmbed(event, homeworkFromDay(homework));
            sendWithDelay(event.getChannel(), mainConfig.getKey(pingRoleIdKey), 1);
        }
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
