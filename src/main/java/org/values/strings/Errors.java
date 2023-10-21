package org.values.strings;

import static org.values.Global.doubleQuotesString;

public class Errors {
    public static String noKeyValueFound = "No value found in config %c for key %k";
    public static String configFileEmpty = "%c config is empty";

    public static String noKeyValueError(String keyName, String configFileName) {
        return replaceConfigAndKey(noKeyValueFound, keyName, configFileName);
    }

    public static String configFileEmptyError(String configFileName) {
        return replaceConfig(configFileEmpty, configFileName);
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

    private static String replaceRegex(String in, String regex, String with) {
        return in.replaceAll(regex, with);
    }
}
