package org.jda.slashcommands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.config.Config;
import org.jda.JdaMain;
import org.jetbrains.annotations.Nullable;

import static org.jda.JdaMain.*;
import static org.main.Varibles.*;
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

    public static boolean isNotOwner(SlashCommandInteractionEvent event) {
        if (!event.getUser().getId().equals(userIdCedric)) {
            replyEmbed(event, notOwnerEmbed(), true);
            return true;
        }
        return false;
    }

    public static boolean isNotAdmin(SlashCommandInteractionEvent event) {
        boolean admin = false;
        for (String id : admins) {
            if (event.getUser().getId().equals(id)) {
                admin = true;
            }
        }
        if (!admin) {
            replyEmbed(event, notAdminEmbed(), true);
        }
        return !admin;
    }

    public static String getHomeworkToDay(String day) {
        return getHomeworkFromCodes(Config.getDaySubj(day), null);
    }

    public static String getHomeworkFromDay(String date) {
        return getHomeworkFromCodes(Config.getAllSubjCodes(), date);
    }

    public static boolean checkGuild(SlashCommandInteractionEvent event) {
        Guild g = event.getGuild();
        if (g == null) {
            return false;
        }
        return event.isFromGuild() && classDiscordServerId.equals(event.getGuild().getId());
    }

    public static boolean checkChannel(SlashCommandInteractionEvent event) {
        return classDiscordHomeworkChannelId.equals(event.getChannel().getId());
    }

    public static void postMessage(SlashCommandInteractionEvent event) {
        String day = getWeekday();
        if (day.equals(saturday) || day.equals(sunday)) {
            replyEmbed(event, notSchoolday(), true);
            return;
        }
        String homework = getHomeworkFromDay(getDate());
        if (homework.equals("")) {
            homework = noHomeworkString;
        }
        if (event == null) {
            TextChannel channel = JdaMain.getTextChannelFromId(classDiscordServerId, classDiscordHomeworkChannelId);
            if (channel != null) {
                sendEmbed(channel, homeworkFromDay(homework));
                sendWithDelay(channel, pingRoleID, 1);
            }
        } else {
            replyEmbed(event, homeworkFromDay(homework));
            sendWithDelay(event.getChannel(), pingRoleID, 1);
        }
    }

    private static String getHomeworkFromCodes(String[] subjCodes, @Nullable String onDate) {
        if (subjCodes == null) {
            return null;
        }
        StringBuilder txt = new StringBuilder();
        for (String subjCode : subjCodes) {
            String hw = Config.getHomework(subjCode);
            String date = Config.getHomeworkDate(subjCode);
            if (hw != null && !hw.equals("") && date != null) {
                if (onDate == null) {
                    txt.append(Config.getSubjFromCode(subjCode)).append(subjsRegex).append(hw).append(newLine);
                } else {
                    if (date.equals(onDate)) {
                        txt.append(Config.getSubjFromCode(subjCode)).append(subjsRegex).append(hw).append(newLine);
                    }
                }
            }
        }
        return txt.toString();
    }
}
