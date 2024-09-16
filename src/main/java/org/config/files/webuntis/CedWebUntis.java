package org.config.files.webuntis;

import de.keule.webuntis.*;
import de.keule.webuntis.response.*;
import org.config.files.records.Subject;

import java.io.IOException;
import java.util.*;

import static org.values.Global.*;
import static org.values.strings.Errors.cantFindUntisClass;

public class CedWebUntis extends WebUntis {

    private static Klasse clazz;

    public CedWebUntis() {
        super(untisSchool, untisServer);
        try {
            this.login();
            Klassen classes = this.getKlassen();
            clazz = classes.getKlasse(untisClassName, true);
            if (clazz == null) {
                this.logout();
                throw new RuntimeException(cantFindUntisClass());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Klasse getKlasse() {
        return clazz;
    }

    public void exit() {
        try {
            this.logout();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Timetable getTimetableForWeek() {
        try {
            return this.getTimetableForWeek(clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Timetable getTimetableForWeekTemp() {
        int startDate = WebUntisDateOperations.getStartDateFromWeek(Calendar.getInstance(), 1);
        try {
            return this.getTimetableForRange(startDate, WebUntisDateOperations.addDaysToDate(startDate, 6), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Subject[] getSubjectsFromTimetable(Timetable timetable, boolean includeCanceled) {
        ArrayList<Subject> subjects = new ArrayList<>();
        for (Period period : timetable.getPeriods()) {
            if ((!period.isCancled()) || (period.isCancled() && includeCanceled)) {
                for (de.keule.webuntis.response.Subject subject : period.getSubjects()) {
                    boolean exists = false;
                    for (Subject sub : subjects) {
                        if (sub.id() == subject.getId()) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        subjects.add(new Subject(subject.getName(), subject.getLongName(), subject.getId()));
                    }
                }
            }
        }
        return subjects.toArray(new Subject[0]);
    }
}
