package org.values.strings;

import net.dv8tion.jda.api.entities.User;

import static org.values.Global.doubleQuotesString;

public class Errors {
    public static String noKeyValueFound = "Could not find value in config %c for key %k";
    public static String configFileEmpty = "%c config is empty";
    public static String noSubjectNameFound = "Could not find subject name for code %k";
    public static String noOptionValue = "Required option value was null when %u executed %cn command";
    public static String optionNotADate = "User %u entered a String that is not a date in %cn command";

    public static String noKeyValueError(String keyName, String configFileName) {
        return replaceConfigAndKey(noKeyValueFound, keyName, configFileName);
    }

    public static String configFileEmptyError(String configFileName) {
        return replaceConfig(configFileEmpty, configFileName);
    }

    public static String noSubjectNameError(String codeName) {
        return replaceKey(noSubjectNameFound, codeName);
    }

    public static String noOptionValue(User user, String commandName) {
        return replaceUserAndCommandName(noOptionValue, user, commandName);
    }

    public static String optionNotADate(User user, String commandName) {
        return replaceUserAndCommandName(optionNotADate, user, commandName);
    }

    private static String replaceConfigAndKey(String in, String key, String config) {
        return replaceKey(replaceConfig(in, config), key);
    }

    private static String replaceConfig(String in, String name) {
        return replaceRegex(in, "%c", doubleQuotesString + name + doubleQuotesString);
    }

    private static String replaceKey(String in, String name) {
        return replaceRegex(in, "%k", doubleQuotesString + name + doubleQuotesString);
    }

    private static String replaceUserAndCommandName(String in, User user, String commandName) {
        return replaceCommandName(replaceUser(in, user), commandName);
    }

    private static String replaceCommandName(String in, String commandName) {
        return replaceRegex(in, "%cn", doubleQuotesString + commandName + doubleQuotesString);
    }

    private static String replaceUser(String in, User user) {
        return replaceRegex(in, "%u", doubleQuotesString + user.getEffectiveName() + doubleQuotesString);
    }

    private static String replaceRegex(String in, String regex, String with) {
        return in.replaceAll(regex, with);
    }
}