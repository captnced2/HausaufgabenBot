package org.main;

import org.jda.JdaMain;
import org.time.Time;

import static org.main.Variables.initVariables;
import static org.time.Time.initDayLoop;
import static org.values.strings.Console.*;

public class Main {

    @SuppressWarnings("FieldCanBeLocal")
    private static JdaMain main;

    public static void main(String[] args) {
        init();
        Thread shutdown = new Thread(Main::shutdownHook);
        Runtime.getRuntime().addShutdownHook(shutdown);
    }

    private static void init() {
        initVariables();
        sendStartingMessage();
        main = new JdaMain();
        main.initJda();
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