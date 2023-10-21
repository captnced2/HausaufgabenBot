package org.time;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.values.Global.*;
import static org.values.strings.Console.sendNextDayLoopScheduled;

public class Time {
    private static CronTrigger loopTrigger;

    public static void initDayLoop() {
        try {
            NewDay.buffer = 0;
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();
            JobDetail job = newJob(NewDay.class).withIdentity("newDayLoopJob", "dayLoop").build();
            loopTrigger = newTrigger().withIdentity("newDayLoopTrigger", "dayLoop").startNow().withSchedule(dailyAtHourAndMinute(14, 0)).build();
            sched.scheduleJob(job, loopTrigger);
            sendNextDayLoopScheduled(loopTrigger.getNextFireTime());
            TimeUnit.SECONDS.sleep(1);
            NewDay.buffer = 1;
        } catch (SchedulerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getNextExecution() {
        return loopTrigger.getNextFireTime();
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

    public static Weekday getWeekday() {
        return getWeekday(0);
    }

    public static Weekday getWeekday(int shift) {
        Weekday returnDay;
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) + shift;
        if (day > 7) {
            day = 1;
        }
        switch (day) {
            case 3 -> returnDay = Weekday.TUESDAY;
            case 4 -> returnDay = Weekday.WEDNESDAY;
            case 5 -> returnDay = Weekday.THURSDAY;
            case 6 -> returnDay = Weekday.FRIDAY;
            case 7 -> returnDay = Weekday.SATURDAY;
            case 1 -> returnDay = Weekday.SUNDAY;
            default -> returnDay = Weekday.MONDAY;
        }
        return returnDay;
    }
}
