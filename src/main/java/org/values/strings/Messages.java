package org.values.strings;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.config.files.records.Subject;
import org.jda.slashcommands.JdaPermission;
import org.time.*;

import java.awt.*;
import java.util.Date;

import static org.jda.slashcommands.SlashCommandGeneral.getHomeworkFromDay;
import static org.time.Time.*;
import static org.values.Global.*;

public class Messages {
    public static final String LukasIsHere = "Luggas ist da!";
    public static final String defaultActivity = "Verwaltet Hausaufgaben...";
    public static final String noHomeworkString = "*Keine Hausaufgaben*";
    public static final String errorTitle = "Error";
    public static final String noPermissionTitle = "Keine Rechte";
    public static final String noPermissionText = "Du hast nicht die benötigten Rechte, um diesen Command auszuführen";
    public static final String somethingWentWrongText = "Etwas ist schiefgelaufen:\n";
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
    public static final String resetHomeworkTitle = "Hausaufgabe zurückgesetzt";
    public static final String changedHomeworkTitle = "Hausaufgabe geändert";
    public static final String noHomeworkFoundTitle = " Hausaufgabe";
    public static final String noHomeworkFoundText = "*Keine Hausaufgabe*";
    public static final String noSubjectFoundTitle = "Fach nicht gefunden";
    public static final String noSubjectFoundText = "\" wurde nicht gefunden, bitte probiere es noch einmal";
    public static final String subjectSelectTitle = "Fächer auswahl";
    public static final String subjectSelectText = "Benutze die Auswahlfelder unter dieser Nachricht, um deine eigenen Fächer auszuwählen\n(Es sind 25 pro Feld wegen Discords limitierungen)";
    public static final String savedChangesTitle = "Änderungen gespeichert :thumbsup:";
    public static final String notYourMessageTitle = "Nicht deine Nachricht!";
    public static final String notYourMessageText = "Finger weg :rage:";
    public static final String permissionsSetTitle = "Rechte gesetzt";
    public static final String permissionsSetText = " ist jetzt ein ";
    public static final String wrongChannelText = "Falscher Channel! Nur in dem hausaufgaben Channel verwendbar.";
    public static final String helpTitle = "Verfügbare Commands für Rechtelevel ";
    public static final String noSubjectsSelected = "Du hast noch nicht deine eigenen Fächer ausgewählt! Hier ist die Nachricht dazu:\n(Du kannst auch jederzeit /selectsubjects verwenden)";

    private static MessageEmbed makeEmbed(Color color, String title, String text) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(color);
        builder.setTitle(title);
        builder.setDescription(text);
        builder.setFooter(getWeekday().getAsString() + embedFooterSeperator + Time.getDateString() + embedFooterSeperator + getCurrentTime());
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

    public static MessageEmbed somethingWentWrongEmbed(String error) {
        return errorTitleEmbed(somethingWentWrongText + error);
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

    public static MessageEmbed subjectSelect() {
        return homeworkEmbed(subjectSelectTitle, subjectSelectText);
    }

    public static MessageEmbed savedChanges() {
        return successEmbed(savedChangesTitle, null);
    }

    public static MessageEmbed notYourMessage() {
        return errorEmbed(notYourMessageTitle, notYourMessageText);
    }

    public static MessageEmbed permissionsSet(User user, JdaPermission permissions) {
        return successEmbed(permissionsSetTitle, user.getName() + permissionsSetText + permissions.toString());
    }

    public static MessageEmbed homeworkFromDay(String homework) {
        return homeworkEmbed(homeworkFromDayTitle + getWeekday().getAsString() + " " + Time.getDateString(), homework);
    }

    public static MessageEmbed postMessageForToday() {
        return homeworkFromDay(getHomeworkFromDay(getDate()));
    }

    public static MessageEmbed homeworkToDate(Weekday day, Date date, String homework) {
        return homeworkEmbed(homeworkToDateTitle + day.getAsString() + " " + getDateAsString(date), homework);
    }

    public static MessageEmbed helpEmbed(JdaPermission permissions, String helpDescriptions) {
        return successEmbed(helpTitle + permissions.getAsInt() + " (" + permissions + ")", helpDescriptions);
    }

    public static MessageEmbed resetHomework(Subject subject) {
        return successEmbed(resetHomeworkTitle, subject.name());
    }

    public static MessageEmbed changedHomework(Subject subject, String homework) {
        return successEmbed(changedHomeworkTitle, subject.name() + subjsRegex + homework);
    }

    public static MessageEmbed noHomeworkFound(Subject subject) {
        return homeworkEmbed(subject.name() + noHomeworkFoundTitle, noHomeworkFoundText);
    }

    public static MessageEmbed noSubjectFound(String subject) {
        return errorEmbed(noSubjectFoundTitle, "\"" + subject + noSubjectFoundText);
    }

    public static MessageEmbed homeworkFromDate(Subject subject, Date date, User user, String homework) {
        return homeworkEmbed("(" + user.getEffectiveName() + ") " + subject.name() + homeworkFromDateTitle + getDateAsString(date), homework);
    }

    public static MessageEmbed wrongChannel() {
        return errorTitleEmbed(wrongChannelText);
    }
}
