package org.values.strings;

import net.dv8tion.jda.api.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.config.Config.writeToLog;
import static org.main.Variables.subjsConfig;
import static org.time.Time.*;
import static org.values.Global.*;

public class Console {
    private static final String startingMessage = "Starting Version %v...";
    private static final String shutdownMessage = "Shutting down...";
    private static final String jdaForceShutdownMessage = "Jda not shutting down. Forcing shutdown";
    private static final String jdaStartupMessage = "Starting Jda...";
    private static final String jdaStartupCompleteMessage = "Jda startup complete";
    private static final String mainConfigCreateEnterValuesMessage = "Created main config file, please enter required values to proceed";
    private static final String cantCreateConfigFileErrorMessage = "Error! Couldn't create config file ";
    private static final String cantCreateFolderErrorMessage = "Error! Couldn't create config folder ";
    private static final String creatingNewFileMessage = "Creating new file ";
    private static final String creatingNewFolderMessage = "Creating new folder ";
    private static final String initCompleteMessage = "Initialization Complete";
    private static final String calledDayLoopMessage = "Called Day Loop Function";
    private static final String doneDayLoopMessage = "Finished Day Loop";
    private static final String postSuccessMessage = "Posted Homework Successfully";
    private static final String postSkipMessage = "Homework Already Posted. Skipping Message";
    private static final String delMessageSuccessMessage = "Sent Deletion Messages To Admins";
    private static final String delMessageSkipMessage = "No Homework To Delete. Skipping Message";
    private static final String addedHomeworkMessage = "%u added in %s the homework: %h";
    private static final String setHomeworkMessage = "%u set %s homework to: %h";
    private static final String requestHomeworkMessage = "%u requested homework for %s";
    private static final String requestHomeworkTomorrowMessage = "%u requested homework for tomorrow";
    private static final String requestHomeworkTodayMessage = "%u requested homework for today";
    private static final String postSuccess = "%u used the post command successfully";
    private static final String delAllMessage = "%u deleted all unchanged homework from today";
    private static final String delSubjMessage = "%u deleted unchanged homework in %s";
    private static final String nextDayLoopAt = "Next Day Loop scheduled for ";
    private static final String commandErrorMessage = "Something went wrong while %u used the %c command";
    private static final String commandRegisterErrorMessage = "Something went wrong while trying to register %c command";
    private static final String registeredCommandsMessage = "Successfully registered %i1/%i2 commands";
    private static final String addedCancelledSubjMessage = "%u added cancelled subject %s on %d";
    private static final String deletedCommandsMessage = "Deleted %i unused commands";

    public static void sendStartingMessage() {
        outLog(replaceIn(startingMessage, "%v", version));
    }

    public static void sendShutdownMessage() {
        outLog(shutdownMessage);
    }

    public static void sendJdaForceShutdownMessage() {
        outLog(jdaForceShutdownMessage);
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

    public static void sendPostSkip() {
        outLog(postSkipMessage);
    }

    public static void sendDelMessageSuccess() {
        outLog(delMessageSuccessMessage);
    }

    public static void sendDelMessageSkip() {
        outLog(delMessageSkipMessage);
    }

    public static void sendAddedHomework(User user, String subjCode, String homework) {
        outLog(replaceHomework(replaceSubjCode(replaceUser(addedHomeworkMessage, user), subjCode), homework));
    }

    public static void sendSetHomework(User user, String subjCode, String homework) {
        outLog(replaceHomework(replaceSubjCode(replaceUser(setHomeworkMessage, user), subjCode), homework));
    }

    public static void sendRequestedHomework(User user, String subjCode) {
        outLog(replaceSubjCode(replaceUser(requestHomeworkMessage, user), subjCode));
    }

    public static void sendRequestedTomorrowHomework(User user) {
        outLog(replaceUser(requestHomeworkTomorrowMessage, user));
    }

    public static void sendRequestedTodayHomework(User user) {
        outLog(replaceUser(requestHomeworkTodayMessage, user));
    }

    public static void sendPostSuccess(User user) {
        outLog(replaceUser(postSuccess, user));
    }

    public static void sendDelAll(User user) {
        outLog(replaceUser(delAllMessage, user));
    }

    public static void sendDelSubj(User user, String subjCode) {
        outLog(replaceSubjCode(replaceUser(delSubjMessage, user), subjCode));
    }

    public static void sendNextDayLoopScheduled(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(weekdayDayMonthYearHoursMinutesPattern);
        outLog(nextDayLoopAt + sdf.format(date));
    }

    public static void sendCommandError(User user, String command) {
        outLog(replaceCommand(replaceUser(commandErrorMessage, user), command));
    }

    public static void sendRegisteredCommands(int count, int total) {
        outLog(replaceIn(replaceIn(registeredCommandsMessage, "%i1", count), "%i2", total));
    }

    public static void sendCommandRegisterError(String command) {
        outLog(replaceCommand(commandRegisterErrorMessage, command));
    }

    public static void sendAddedCancelledSubj(User user, String subjCode, String date) {
        outLog(replaceDate(replaceSubjCode(replaceUser(addedCancelledSubjMessage, user), subjCode), date));
    }

    public static void sendDeletedCommands(int count) {
        outLog(replaceIn(deletedCommandsMessage, "%i", String.valueOf(count)));
    }

    private static String replaceIn(String string, String regex, String with) {
        return string.replaceFirst(regex, with);
    }

    private static String replaceIn(String string, String regex, int with) {
        return replaceIn(string, regex, String.valueOf(with));
    }

    private static String replaceUser(String string, User user) {
        return replaceIn(string, "%u", user.getEffectiveName());
    }

    private static String replaceSubjCode(String string, String subjCode) {
        return replaceIn(string, "%s", surroundQuotes(subjsConfig.getNameFromCode(subjCode)));
    }

    private static String replaceDate(String string, String date) {
        return replaceIn(string, "%d", date);
    }

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
        writeToLog(getDate() + " " + getCurrentTime() + " : " + out);
    }

    private static void out(String out) {
        System.out.println(getDate() + " " + getCurrentTime() + " : " + out);
    }
}
