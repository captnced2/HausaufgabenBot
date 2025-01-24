package org.config.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.User;
import org.config.ConfigFile;
import org.config.files.records.*;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

import static org.time.Time.getDate;

public class HomeworkConfig extends ConfigFile {

    final ObjectMapper mapper;
    final File file;

    public HomeworkConfig(String fileName) {
        super(fileName);
        mapper = new ObjectMapper();
        file = new File(getConfigFilePath());
    }

    @Override
    protected String getTemplate() {
        return "[]";
    }

    private ArrayList<HomeworkInstance> getAllHomework() {
        try {
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HomeworkInstance getHomework(Subject subject) {
        for (HomeworkInstance h : getAllHomework()) {
            if (subject.id() == h.subjectId()) {
                return h;
            }
        }
        return new HomeworkInstance(subject.id(), null, null, 0);
    }

    public void resetHomework(Subject subject) {
        setHomework(subject, null, null, null);
    }

    public void resetHomeworkIfOld(Subject subject) {
        HomeworkInstance h = getHomework(subject);
        if (h.date() != null) {
            if (h.date().before(getDate())) {
                resetHomework(subject);
            }
        }
    }

    public void setHomework(Subject subject, String value, User user) {
        setHomework(subject, getDate(), value, user);
    }

    public void setHomework(Subject subject, Date date, String value, @Nullable User user) {
        ArrayList<HomeworkInstance> homework = getAllHomework();
        long id = 0;
        if (user != null) {
            id = user.getIdLong();
        }
        HomeworkInstance hw = new HomeworkInstance(subject.id(), date, value, id);
        boolean newHw = true;
        for (HomeworkInstance h : homework) {
            if (subject.id() == h.subjectId()) {
                homework.set(homework.indexOf(h), hw);
                newHw = false;
            }
        }
        if (newHw) {
            homework.add(hw);
        }
        try {
            mapper.writeValue(file, homework);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
