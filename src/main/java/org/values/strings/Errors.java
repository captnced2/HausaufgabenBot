package org.values.strings;

import net.dv8tion.jda.api.entities.User;

import static org.values.Global.*;

public class Errors {
    private static final String noKeyValueFound = "Could not find value in config %c for key %k";
    private static final String configFileEmpty = "%c config is empty";
    private static final String noSubjectNameFound = "Could not find subject for code %k";
    private static final String noOptionValue = "Required option value was null when %u executed %cn command";
    private static final String cantFindUntisClass = "Could not find Untis class ";

    public static String noKeyValueError(String keyName, String configFileName) {
        return replaceConfigAndKey(noKeyValueFound, keyName, configFileName);
    }

    public static String configFileEmptyError(String configFileName) {
        return replaceConfig(configFileEmpty, configFileName);
    }

    public static String noSubjectNameError(String subjCode) {
        return replaceKey(noSubjectNameFound, subjCode);
    }

    public static String noOptionValue(User user, String commandName) {
        return replaceCommandName(replaceUser(noOptionValue, user), commandName);
    }

    public static String cantFindUntisClass() {
        return cantFindUntisClass + className;
    }

    @SuppressWarnings("SameParameterValue")
    private static String replaceConfigAndKey(String in, String key, String config) {
        return replaceKey(replaceConfig(in, config), key);
    }

    private static String replaceConfig(String in, String name) {
        return replaceRegex(in, "%c", doubleQuotesString + name + doubleQuotesString);
    }

    private static String replaceKey(String in, String name) {
        return replaceRegex(in, "%k", doubleQuotesString + name + doubleQuotesString);
    }

    private static String replaceCommandName(String in, String commandName) {
        return replaceRegex(in, "%cn", doubleQuotesString + commandName + doubleQuotesString);
    }

    @SuppressWarnings("SameParameterValue")
    private static String replaceUser(String in, User user) {
        return replaceRegex(in, "%u", doubleQuotesString + user.getEffectiveName() + doubleQuotesString);
    }

    private static String replaceRegex(String in, String regex, String with) {
        return in.replaceAll(regex, with);
    }
}