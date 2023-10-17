package org.time;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.Config;
import org.jda.JdaMain;
import org.jda.slashcommands.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Varibles.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.acceptDelHomework;

public class Time {
    private static int buffer;
    private static Calendar nextExecution;

    public static void initDayLoop() {
        buffer = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                newDay();
            }
        };
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 14);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        if (Calendar.getInstance().getTime().after(date.getTime())) {
            date.add(Calendar.DATE, 1);
        }
        timer.schedule(task, date.getTime(), oneDayInMilliseconds);
        sendNextDayLoopScheduled(date.getTime());
        nextExecution = date;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
        buffer = 1;
    }

    public static void newDay() {
        if (buffer == 0) {
            return;
        }
        sendCalledDayLoopMessage();
        String day = getWeekday();
        if (!(day.equals(saturday) || day.equals(sunday))) {
            if (!alreadyPosted.equals(getDate())) {
                SlashCommandGeneral.postMessage(null);
                sendPostSuccess();
            } else {
                sendPostSkip();
            }
            String[] pendingDel = Config.getPendingDelSubj();
            if (pendingDel == null) {
                JdaMain.resetAcceptDelCommand();
                sendDelMessageSkip();
            } else {
                StringBuilder txt = new StringBuilder();
                OptionData faecher = new OptionData(OptionType.STRING, OptionSubjName, OptionSubjDescription, true);
                faecher.addChoice(ChoiceAllName, ChoiceAllValue);
                for (String s : pendingDel) {
                    faecher.addChoice(Config.getSubjFromCode(s), s);
                    txt.append(Config.getSubjFromCode(s)).append(newLine);
                }
                JdaSlashCommand acceptDelCommand = JdaMain.getCommandFromName(AcceptDelName);
                if (acceptDelCommand == null) {
                    return;
                }
                JdaMain.setCommand(acceptDelCommand, faecher);
                MessageEmbed embed = acceptDelHomework(txt.toString());
                for (String a : admins) {
                    JdaMain.sendPrivateMessage(a, embed);
                }
                sendDelMessageSuccess();
            }
        }
        sendDoneDayLoopMessage();
        nextExecution.add(Calendar.DATE, 1);
        sendNextDayLoopScheduled(nextExecution.getTime());
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(hoursMinutesPattern);
        return sdf.format(cal.getTime());
    }

    public static String getDate() {
        return getDate(0);
    }

    public static String getDate(int shift) {
        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DATE, shift);
        dt = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dayMonthYearPattern);
        return sdf.format(dt);
    }

    public static String getWeekday() {
        return getWeekday(0);
    }

    public static String getWeekday(int shift) {
        String returnDay = "2";
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) + shift;
        if (day > 7) {
            day = 1;
        }
        switch (day) {
            case 2 -> returnDay = monday;
            case 3 -> returnDay = tuesday;
            case 4 -> returnDay = wednesday;
            case 5 -> returnDay = thursday;
            case 6 -> returnDay = friday;
            case 7 -> returnDay = saturday;
            case 1 -> returnDay = sunday;
        }
        return returnDay;
    }
}
