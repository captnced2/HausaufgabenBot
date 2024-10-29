package org.config.files.webuntis;


import org.config.files.records.Subject;

import java.io.IOException;
import java.util.Date;

import static org.time.Time.getWebUntisDateFromDate;
import static org.values.strings.Errors.noSubjectFoundError;

public class WebUntisAPI {

    private static final Subject[] allSubjects;

    static {
        CedWebUntis untis = new CedWebUntis();
        allSubjects = untis.getSubjectsFromTimetable(untis.getTimetable(), true);
        untis.exit();
    }

    public static Subject[] getSubjsOnDate(Date date) {
        CedWebUntis untis = new CedWebUntis();
        Subject[] subjects;
        try {
            subjects = untis.getSubjectsFromTimetable(untis.getTimetableFor(getWebUntisDateFromDate(date), 1, untis.getKlasse().getId()), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        untis.exit();
        return subjects;
    }

    public static Subject getSubjectFromName(String subjectName) {
        for (Subject subject : allSubjects) {
            if (subject.name().equals(subjectName)) {
                return subject;
            }
        }
        throw new RuntimeException(noSubjectFoundError(subjectName));
    }

    public static Subject[] getAllSubjects() {
        return allSubjects;
    }
}
