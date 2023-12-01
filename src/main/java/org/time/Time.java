package org.time;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.values.Global.*;

public class Time {
    private static CronTrigger loopTrigger;
    private static Scheduler scheduler;

    public static void initDayLoop() {
        try {
            NewDay.buffer = 0;
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();
            scheduler.start();
            JobDetail job = newJob(NewDay.class).withIdentity("newDayLoopJob", "dayLoop").build();
            loopTrigger = newTrigger().withIdentity("newDayLoopTrigger", "dayLoop").withSchedule(cronSchedule("0 0 14 ? * *")).build();
            scheduler.scheduleJob(job, loopTrigger);
            TimeUnit.SECONDS.sleep(1);
            NewDay.buffer = 1;
        } catch (SchedulerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
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

    public static Date getDate() {
        return getDate(0);
    }

    public static Date getDate(int shift) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, shift);
        return cal.getTime();
    }

    public static String getDateString() {
        return getDateString(0);
    }

    public static String getDateString(int shift) {
        Date dt = getDate(shift);
        return formatDate(dt);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dayMonthYearPattern);
        return sdf.format(date);
    }

    public static Weekday getWeekday() {
        return getWeekday(0);
    }

    public static Weekday getWeekday(int shift) {
        Weekday returnDay;
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) + shift;
        if (day > 7) {
            day = day - 7;
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

    public static boolean isWeekend() {
        Weekday weekday = getWeekday();
        return weekday == Weekday.SATURDAY || weekday == Weekday.SUNDAY;
    }
}
