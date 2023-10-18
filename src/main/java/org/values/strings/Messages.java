package org.values.strings;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

import static org.config.Config.getSubjFromCode;
import static org.time.Time.*;
import static org.values.Global.*;

public class Messages {
    public static final String waitingOnLukasMessage = "Wartet auf Luggas...";
    public static final String LukasIsHere = "Luggas ist da!";
    public static final String defaultActivity = "Verwaltet Hausaufgaben...";
    public static final String noHomeworkString = "*Keine Hausaufgaben*";
    public static final String errorTitle = "Error";
    public static final String noPermissionTitle = "Keine Rechte";
    public static final String noPermissionText = "Du hast nicht die benötigten Rechte, um diesen Command auszuführen";
    public static final String somethingWentWrongText = "Etwas ist schiefgelaufen";
    public static final String shutdownTitle = "Shutting down...";
    public static final String shutdownText = "Good night :D ...";
    public static final String triggeredDayLoopTitle = "Day loop ausgeführt";
    public static final String triggeredDayLoopText = "Geht mal wieder nicht...";
    public static final String notSchooldayText = "Heute ist kein Schultag!";
    public static final String notSchooldayTomorrow = "Morgen ist keine Schule... Aber hier sind die Hausaufgaben für Montag :D.";
    public static final String notSchooldayToday = "Heute ist keine Schule...";
    public static final String homeworkFromDayTitle = "Hausaufgaben vom ";
    public static final String homeworkFromDateTitle = " Hausaufgabe vom ";
    public static final String homeworkToDateTitle = "Hausaufgaben auf ";
    public static final String addedHomeworkTitle = "Hausaufgabe hinzugefügt";
    public static final String changedHomeworkTitle = "Hausaufgabe geändert";
    public static final String noHomeworkFoundTitle = " Hausaufgabe";
    public static final String noHomeworkFoundText = "*Keine Hausaufgabe*";
    public static final String wrongServerText = "Falscher Server! Nur auf dem 11a Discord Server verwendbar.";
    public static final String wrongChannelText = "Falscher Channel! Nur in dem hausaufgaben Channel verwendbar.";
    public static final String onlyUsableOnWeekdaysText = "Dieser Command ist nur an Wochentagen verwendbar.";
    public static final String noHomeworkToDeleteText = "Es gibt keine Hausaufgaben zum Löschen.";
    public static final String deletedAllHomeworkTitle = "Hausaufgaben gelöscht";
    public static final String deletedHomeworkTitle = "Hausaufgabe gelöscht";
    public static final String acceptDelHomeworkTitle = "Hausaufgaben zur Löschung freigeben";
    public static final String addedCancelledSubjTitle = "Fach als entfällt eingetragen";

    private static MessageEmbed makeEmbed(Color color, String title, String text) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(color);
        builder.setTitle(title);
        builder.setDescription(text);
        builder.setFooter(getWeekday() + embedFooterSeperator + getDate() + embedFooterSeperator + getCurrentTime());
        return builder.build();
    }

    private static MessageEmbed successEmbed(String title, String text) {
        return makeEmbed(successEmbedColor, title, text);
    }

    private static MessageEmbed errorEmbed(String title, String text) {
        return makeEmbed(errorEmbedColor, title, text);
    }

    public static MessageEmbed errorTitleEmbed(String text) {
        return errorEmbed(errorTitle, text);
    }

    public static MessageEmbed homeworkEmbed(String title, String text) {
        return makeEmbed(homeworkEmbedColor, title, text);
    }

    public static MessageEmbed noPermissionsEmbed() {
        return errorEmbed(noPermissionTitle, noPermissionText);
    }

    public static MessageEmbed somethingWentWrongEmbed() {
        return errorTitleEmbed(somethingWentWrongText);
    }

    public static MessageEmbed shutdownEmbed() {
        return successEmbed(shutdownTitle, shutdownText);
    }

    public static MessageEmbed triggeredDayLoopEmbed() {
        return successEmbed(triggeredDayLoopTitle, triggeredDayLoopText);
    }

    public static MessageEmbed notSchoolday() {
        return errorTitleEmbed(notSchooldayText);
    }

    public static MessageEmbed homeworkFromDay(String homework) {
        return homeworkEmbed(homeworkFromDayTitle + getWeekday() + " " + getDate(), homework);
    }

    public static MessageEmbed homeworkToDate(String day, String date, String homework) {
        return homeworkEmbed(homeworkToDateTitle + day + " " + date, homework);
    }

    public static MessageEmbed addedHomework(String subjCode, String homework) {
        return successEmbed(addedHomeworkTitle, getSubjFromCode(subjCode) + subjsRegex + homework);
    }

    public static MessageEmbed changedHomework(String subjCode, String homework) {
        return successEmbed(changedHomeworkTitle, getSubjFromCode(subjCode) + subjsRegex + homework);
    }

    public static MessageEmbed noHomeworkFound(String subjCode) {
        return homeworkEmbed(getSubjFromCode(subjCode) + noHomeworkFoundTitle, noHomeworkFoundText);
    }

    public static MessageEmbed homeworkFromDate(String subjCode, String date, String homework) {
        return homeworkEmbed(getSubjFromCode(subjCode) + homeworkFromDateTitle + date, homework);
    }

    public static MessageEmbed addedCancelledSubj(String subjCode, String date) {
        return successEmbed(addedCancelledSubjTitle, date + subjsRegex + getSubjFromCode(subjCode));
    }

    public static MessageEmbed wrongServer() {
        return errorTitleEmbed(wrongServerText);
    }

    public static MessageEmbed wrongChannel() {
        return errorTitleEmbed(wrongChannelText);
    }

    public static MessageEmbed onlyUsableOnWeekdays() {
        return errorTitleEmbed(onlyUsableOnWeekdaysText);
    }

    public static MessageEmbed noHomeworkToDelete() {
        return errorTitleEmbed(noHomeworkToDeleteText);
    }

    public static MessageEmbed deletedAllHomework(String homework) {
        return successEmbed(deletedAllHomeworkTitle, homework);
    }

    public static MessageEmbed deletedHomework(String subjCode) {
        return successEmbed(deletedHomeworkTitle, getSubjFromCode(subjCode));
    }

    public static MessageEmbed acceptDelHomework(String homework) {
        return homeworkEmbed(acceptDelHomeworkTitle, homework);
    }
}
