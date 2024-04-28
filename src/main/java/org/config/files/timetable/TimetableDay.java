package org.config.files.timetable;

import org.config.files.subjects.Subject;
import org.time.*;

import java.util.*;

public class TimetableDay {

    private final Date date;
    private final Weekday weekday;
    private final ArrayList<TimetableLesson> lessons;

    public TimetableDay(Date date) {
        this.date = date;
        weekday = Time.getWeekdayFromDate(date);
        lessons = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public Weekday getWeekday() {
        return weekday;
    }

    public Date getDate() {
        return date;
    }

    public void addLesson(TimetableLesson lesson) {
        lessons.add(lesson);
    }

    public Subject[] getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        for (TimetableLesson lesson : lessons) {
            subjects.add(lesson.subject());
        }
        return subjects.toArray(new Subject[0]);
    }
}
