package org.main;

import org.jda.JdaMain;
import org.time.Time;

import static org.config.Config.initConfigs;
import static org.jda.JdaMain.initJda;
import static org.main.Variables.initVariables;
import static org.time.Time.initDayLoop;
import static org.values.strings.Console.*;

public class Main {

    public static void main(String[] args) {
        init();
        Thread shutdown = new Thread(Main::shutdownHook);
        Runtime.getRuntime().addShutdownHook(shutdown);
    }

    private static void init() {
        initVariables();
        sendStartingMessage();
        initConfigs();
        initJda();
        initDayLoop();
        sendInitComplete();
    }

    public static void shutdownHook() {
        JdaMain.setOffline();
    }

    public static void shutdown() {
        sendShutdownMessage();
        Time.shutdown();
        JdaMain.shutdown();
    }
}