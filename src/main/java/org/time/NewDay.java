package org.time;

import org.quartz.*;

import static org.jda.JdaMain.*;
import static org.main.Variables.*;
import static org.time.Time.isWeekend;
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
        timetableConfig.updateTimeTable();
        if (isWeekend() || holidayConfig.isHolidayDay(Time.getDate())) {
            return;
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
