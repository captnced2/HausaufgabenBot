package org.config.files.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.config.ConfigFile;
import org.config.files.subjects.Subject;
import org.time.Time;

import java.io.*;
import java.util.*;

import static org.main.Variables.subjsConfig;

public class TimetableConfig extends ConfigFile {

    ArrayList<TimetableDay> days;

    public TimetableConfig(String fileName) {
        super(fileName);
        updateTimeTable();
    }

    @Override
    protected String getTemplate() {
        return "";
    }

    private void updateTimeTable() {
        updateTimeTableFile();
        getLessons();
    }

    public Subject[] getSubjsOnDate(Date date) {
        updateTimeTable();
        return getDayFromDate(date).getAllSubjects();
    }

    private void updateTimeTableFile() {
        try {
            String path = "node JavaScript/bin/TimetableRequester.js";
            Process javascript = Runtime.getRuntime().exec(path, null, new File(System.getProperty("user.dir")));
            javascript.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLessons() {
        try {
            String lines = Arrays.toString(getLines());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(lines).get(0);
            days = new ArrayList<>();
            for (JsonNode lesson : node) {
                if (lesson.get("activityType") != null && lesson.get("activityType").asText().equals("Unterricht") && (lesson.get("code") == null || lesson.get("code").asText().equals("irregular")) && lesson.get("su").get(0) != null) {
                    Date lessonDate = Time.getDateFromWebUntis(lesson.get("date").asText());
                    TimetableDay day = getDayFromDate(lessonDate);
                    Subject subject = subjsConfig.getSubjectFromWebuntis(lesson.get("su").get(0).get("name").asText());
                    if (subject != null) {
                        boolean duplicate = false;
                        for (Subject subj : day.getAllSubjects()) {
                            if (subj == subject) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            day.addLesson(new TimetableLesson(subjsConfig.getSubjectFromWebuntis(lesson.get("su").get(0).get("name").asText())));
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private TimetableDay getDayFromDate(Date date) {
        for (TimetableDay day : days) {
            if (day.getDate().equals(date)) {
                return day;
            }
        }
        TimetableDay newDay = new TimetableDay(date);
        days.add(newDay);
        return newDay;
    }
}
