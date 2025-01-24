package org.config.files.records;

import java.util.Date;

public record HomeworkInstance(int subjectId, Date date, String value, long userId) {
}
