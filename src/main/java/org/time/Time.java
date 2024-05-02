package org.time;

import org.quartz.*;

import java.text.*;
import java.util.Calendar;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.values.Global.*;

public class Time {
    private static Scheduler scheduler;

    public static void initDayLoop() {
        try {
            NewDay.buffer = 0;
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();
            scheduler.start();
            JobDetail job = newJob(NewDay.class).withIdentity("newDayLoopJob", "dayLoop").build();
            CronTrigger loopTrigger = newTrigger().withIdentity("newDayLoopTrigger", "dayLoop").withSchedule(cronSchedule("0 0 14 ? * *")).build();
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

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(hoursMinutesPattern);
        return sdf.format(cal.getTime());
    }

    public static Date getDate() {
        return getDate(0);
    }

    @SuppressWarnings("MagicConstant")
    public static Date getDate(int shift) {
        Calendar cal = Calendar.getInstance();
        int[] ymd = new int[]{cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)};
        cal.clear();
        cal.set(ymd[0], ymd[1], ymd[2]);
        cal.add(Calendar.DATE, shift);
        return cal.getTime();
    }

    public static String getDateString() {
        return getDateString(0);
    }

    public static Date getDateFromString(String dateString) {
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDateString(int shift) {
        Date dt = getDate(shift);
        return formatDate(dt);
    }

    public static String getDateAsString(Date date) {
        return formatDate(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dayMonthYearPattern);
        return sdf.format(date);
    }

    public static Date getDateFromWebUntis(String date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6, 8)));
        return cal.getTime();
    }

    public static Weekday getWeekday() {
        return getWeekday(0);
    }

    public static Weekday getWeekday(int shift) {
        return getWeekdayFromDate(getDate(shift));
    }

    public static Weekday getWeekdayFromDate(Date date) {
        Weekday returnDay;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
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
