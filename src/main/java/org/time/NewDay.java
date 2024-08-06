package org.time;

import org.config.files.subjects.Subject;
import org.quartz.*;

import static org.config.WebUntisAPI.getSubjsOnDate;
import static org.jda.JdaMain.*;
import static org.main.Variables.homeworkConfig;
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
        if (isWeekend()) {
            return;
        }
        for (Subject subject : getSubjsOnDate(getDate())) {
            homeworkConfig.resetHomeworkIfOld(subject);
        }
        sendEmbedToChannelByName(homeworkChannel, postMessageForToday());
        sendPostSuccess();
        updateDateOption();
        sendDoneDayLoopMessage();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        newDay();
    }
}
