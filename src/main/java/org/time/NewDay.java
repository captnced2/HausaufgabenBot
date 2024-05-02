package org.time;

import org.config.files.subjects.Subject;
import org.quartz.*;

import static org.jda.JdaMain.*;
import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.homeworkChannel;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.postMessageForToday;

public class NewDay implements Job {
    public static int buffer;

    public void newDay() {
        if (buffer == 0) {
            return;
        }
        sendCalledDayLoopMessage();
        if (isWeekend() || holidayConfig.isHolidayDay(Time.getDate())) {
            return;
        }
        for (Subject subject : timetableConfig.getSubjsOnDate(getDate())) {
            homeworkConfig.resetHomeworkIfOld(subject);
        }
        sendEmbedToChannelByNameWithPing(homeworkChannel, postMessageForToday());
        sendPostSuccess();
        updateDateOption();
        sendDoneDayLoopMessage();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        newDay();
    }
}
