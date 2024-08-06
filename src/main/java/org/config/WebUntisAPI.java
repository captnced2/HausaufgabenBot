package org.config;

import de.keule.webuntis.WebUntis;
import de.keule.webuntis.response.*;
import org.config.files.subjects.Subject;

import java.io.IOException;
import java.util.*;

import static org.main.Variables.subjsConfig;
import static org.time.Time.getDateFromWebUntis;
import static org.values.Global.className;
import static org.values.strings.Errors.cantFindUntisClass;

public class WebUntisAPI {
    private static final WebUntis untis = new WebUntis("Gym_MT", "nessa.webuntis.com");

    public static Subject[] getSubjsOnDate(Date date) {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            untis.login();
            Klassen classes = untis.getKlassen();
            Klasse clazz = classes.getKlasse(className, true);
            if (clazz == null) {
                throw new RuntimeException(cantFindUntisClass());
            }
            Timetable timetable = untis.getTimetableForWeek(clazz);
            untis.logout();
            for (Period period : timetable.getPeriods()) {
                if (getDateFromWebUntis(String.valueOf(period.getDate())) == date) {
                    for (de.keule.webuntis.response.Subject subject : period.getSubjects()) {
                        subjects.add(subjsConfig.getSubjectFromWebuntis(subject.getName()));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return subjects.toArray(new Subject[0]);
    }
}
