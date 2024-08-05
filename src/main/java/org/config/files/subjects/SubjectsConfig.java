package org.config.files.subjects;

import org.config.ConfigFile;

import java.util.ArrayList;

import static org.values.Global.keySeperator;
import static org.values.strings.Errors.noSubjectNameError;

public class SubjectsConfig extends ConfigFile {

    private ArrayList<Subject> subjects;

    public SubjectsConfig(String fileName) {
        super(fileName);
        getSubjects();
    }

    @Override
    protected String getTemplate() {
        return """
                # Fächer Abkürzungen im Schema {FACHNAME}={CODE}={WEBUNTISCODE}
                """;
    }

    private void getSubjects() {
        String[] lines = getLines();
        subjects = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(keySeperator);
            subjects.add(new Subject(parts[0], parts[1], parts[2]));
        }
    }

    public Subject getSubjectFromWebuntis(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.webuntis().equals(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    public Subject getSubjectFromCode(String subjCode) {
        for (Subject subject : subjects) {
            if (subject.code().equals(subjCode)) {
                return subject;
            }
        }
        throw new RuntimeException(noSubjectNameError(subjCode));
    }

    public Subject[] getAllSubjects() {
        return subjects.toArray(new Subject[0]);
    }
}
