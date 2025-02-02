package org.main;

import org.jda.JdaMain;
import org.time.Time;

import java.io.IOException;
import java.net.*;

import static org.main.Variables.initVariables;
import static org.time.Time.initDayLoop;
import static org.values.strings.Console.*;

public class Main {

    @SuppressWarnings("FieldCanBeLocal")
    private static JdaMain main;

    public static void main(String[] args) {
        if (!checkInternetConnection()) {
            sendNoInternetConnection();
            return;
        }
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

    private static boolean checkInternetConnection() {
        try (Socket socket = new Socket()) {
            InetSocketAddress addr = new InetSocketAddress("google.com", 80);
            socket.connect(addr, 3000);
        } catch (IOException e) {
            return false;
        }
        return true;
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