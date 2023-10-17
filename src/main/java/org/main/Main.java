package org.main;

import org.jda.JdaMain;

import static org.config.Config.*;
import static org.main.Varibles.initVariables;
import static org.time.Time.initDayLoop;
import static org.values.strings.Console.*;

public class Main {

    public static void main(String[] args) {
        init();
        Thread shutdown = new Thread(Main::shutdown);
        Runtime.getRuntime().addShutdownHook(shutdown);
    }

    private static void init() {
        initVariables();
        initCache();
        sendStartingMessage();
        initConfigs();
        JdaMain.initJda();
        initDayLoop();
        sendInitComplete();
    }

    public static void shutdown() {
        sendShutdownMessage();
        JdaMain.shutdown();
    }
}