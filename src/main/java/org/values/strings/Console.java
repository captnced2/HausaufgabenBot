package org.values.strings;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.config.files.records.Subject;
import org.jda.slashcommands.JdaPermission;
import org.time.Time;

import static org.config.ConfigManager.writeToLog;
import static org.time.Time.getCurrentTime;
import static org.values.Global.*;

public class Console {
    private static final String startingMessage = "Starting Version %v...";
    private static final String shutdownMessage = "Shutting down...";
    private static final String jdaForceShutdownMessage = "Jda not shutting down. Forcing shutdown";
    private static final String noInternetConnection = "No internet connection!";
    private static final String jdaStartupMessage = "Starting Jda...";
    private static final String jdaStartupCompleteMessage = "Jda startup complete";
    private static final String mainConfigCreateEnterValuesMessage = "Created main config file, please enter required values to proceed";
    private static final String cantChangeAvatarMessage = "Can't change Avatar. This message can probably be ignored";
    private static final String cantCreateConfigFileErrorMessage = "Error! Couldn't create config file ";
    private static final String cantCreateFolderErrorMessage = "Error! Couldn't create config folder ";
    private static final String creatingNewFileMessage = "Creating new file ";
    private static final String creatingNewFolderMessage = "Creating new folder ";
    private static final String initCompleteMessage = "Initialization Complete";
    private static final String calledDayLoopMessage = "Called Day Loop Function";
    private static final String doneDayLoopMessage = "Finished Day Loop";
    private static final String postSuccessMessage = "Posted Homework Successfully";
    private static final String setHomeworkMessage = "%u set %s homework to: %h";
    private static final String resetHomeworkMessage = "%u reset %s homework";
    private static final String requestHomeworkMessage = "%u requested homework for %s";
    private static final String requestHomeworkTomorrowMessage = "%u requested homework for tomorrow";
    private static final String changedPersonalSubjectsMessage = "%u changed their personal subjects";
    private static final String requestHomeworkTodayMessage = "%u requested homework for today";
    private static final String postSuccess = "%u used the post command successfully";
    private static final String commandRegisterErrorMessage = "Something went wrong while trying to register %c command";
    private static final String registeredCommandsMessage = "Successfully registered %i1/%i2 commands";
    private static final String deletedCommandsMessage = "Deleted %i unused commands";
    private static final String anErrorOccurredMessage = "An Error occurred: ";
    private static final String askedForHelp = "%u asked for help";
    private static final String setPermissionsMessage = "%u set %u permissions to %p";

    public static void sendStartingMessage() {
        outLog(replaceIn(startingMessage, "%v", version));
    }

    public static void sendShutdownMessage() {
        outLog(shutdownMessage);
    }

    public static void sendJdaForceShutdownMessage() {
        outLog(jdaForceShutdownMessage);
    }

    public static void sendNoInternetConnection() {
        outLog(noInternetConnection);
    }

    public static void sendCantChangeAvatar() {
        out(cantChangeAvatarMessage);
    }

    public static void sendMainConfigCreatedEnterValues() {
        outLog(mainConfigCreateEnterValuesMessage);
    }

    public static void sendCreatingNewFile(String config) {
        outLog(creatingNewFileMessage + config);
    }

    public static void sendCreatingNewFolder(String folder) {
        outLog(creatingNewFolderMessage + folder);
    }

    public static void sendCantCreateConfError(String config) {
        outLog(cantCreateConfigFileErrorMessage + config);
    }

    public static void sendCantCreateFolderError(String folder) {
        outLog(cantCreateFolderErrorMessage + folder);
    }

    public static void sendJdaStartup() {
        out(jdaStartupMessage);
    }

    public static void sendJdaStartupComplete() {
        out(jdaStartupCompleteMessage);
    }

    public static void sendInitComplete() {
        outLog(initCompleteMessage);
    }

    public static void sendCalledDayLoopMessage() {
        outLog(calledDayLoopMessage);
    }

    public static void sendDoneDayLoopMessage() {
        outLog(doneDayLoopMessage);
    }

    public static void sendPostSuccess() {
        outLog(postSuccessMessage);
    }

    public static void sendSetHomework(SlashCommandInteractionEvent event, Subject subject, String homework) {
        outLog(replaceHomework(replaceSubjCode(replaceUserWithEvent(setHomeworkMessage, event), subject), homework));
    }

    public static void sendResetHomework(SlashCommandInteractionEvent event, Subject subject) {
        outLog(replaceUserWithEvent(replaceSubjCode(resetHomeworkMessage, subject), event));
    }

    public static void sendRequestedHomework(SlashCommandInteractionEvent event, Subject subject) {
        outLog(replaceSubjCode(replaceUserWithEvent(requestHomeworkMessage, event), subject));
    }

    public static void sendRequestedTomorrowHomework(SlashCommandInteractionEvent event) {
        outLog(replaceUserWithEvent(requestHomeworkTomorrowMessage, event));
    }

    public static void sendChangedSubjects(User user) {
        outLog(replaceUser(changedPersonalSubjectsMessage, user));
    }

    public static void sendSetPermissions(SlashCommandInteractionEvent event, User permsUser, JdaPermission perms) {
        outLog(replaceIn(replaceUser(replaceUserWithEvent(setPermissionsMessage, event), permsUser), "%p", perms.toString()));
    }

    public static void sendAskedForHelp(SlashCommandInteractionEvent event) {
        outLog(replaceUserWithEvent(askedForHelp, event));
    }

    public static void sendRequestedTodayHomework(SlashCommandInteractionEvent event) {
        outLog(replaceUserWithEvent(requestHomeworkTodayMessage, event));
    }

    public static void sendPostSuccess(User user) {
        outLog(replaceUser(postSuccess, user));
    }

    public static void sendRegisteredCommands(int count, int total, String commandList) {
        outLog(replaceIn(replaceIn(registeredCommandsMessage, "%i1", count), "%i2", total) + subjsRegex + commandList);
    }

    public static void sendCommandRegisterError(String command) {
        outLog(replaceCommand(commandRegisterErrorMessage, command));
    }

    public static void sendDeletedCommands(int count) {
        outLog(replaceIn(deletedCommandsMessage, "%i", String.valueOf(count)));
    }

    public static void sendError(String message) {
        out(anErrorOccurredMessage + message);
    }

    private static String replaceIn(String string, String regex, String with) {
        return string.replaceFirst(regex, with);
    }

    private static String replaceIn(String string, String regex, int with) {
        return replaceIn(string, regex, String.valueOf(with));
    }

    private static String replaceUserWithEvent(String string, SlashCommandInteractionEvent event) {
        String replaceString = event.getUser().getEffectiveName() + " (@" + event.getUser().getName() + ") in " + event.getChannelType();
        if (event.getChannelType() == ChannelType.TEXT) {
            replaceString += " \"" + event.getChannel().getName() + "\" - Server \"" + event.getGuildChannel().getGuild().getName() + "\"";
        }
        return replaceIn(string, "%u", replaceString);
    }

    private static String replaceUser(String string, User user) {
        return replaceIn(string, "%u", user.getEffectiveName() + " (@" + user.getName() + ")");
    }

    private static String replaceSubjCode(String string, Subject subject) {
        return replaceIn(string, "%s", surroundQuotes(subject.name()));
    }

    @SuppressWarnings("unused")
    private static String replaceDate(String string, String date) {
        return replaceIn(string, "%d", date);
    }

    @SuppressWarnings("SameParameterValue")
    private static String replaceCommand(String string, String command) {
        return replaceIn(string, "%c", surroundQuotes(command));
    }

    private static String replaceHomework(String string, String homework) {
        return replaceIn(string, "%h", surroundQuotes(homework));
    }

    private static String surroundQuotes(String in) {
        return doubleQuotesString + in + doubleQuotesString;
    }

    private static void outLog(String out) {
        out(out);
        writeToLog(outMessage(out));
    }

    private static void out(String out) {
        System.out.println(outMessage(out));
    }

    private static String outMessage(String out) {
        return "[" + Time.getDateString() + " - " + getCurrentTime() + "] : " + out;
    }
}
