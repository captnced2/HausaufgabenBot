package org.time;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.Config;
import org.jda.JdaMain;
import org.jda.slashcommands.*;
import org.quartz.*;

import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.newLine;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.acceptDelHomework;

public class NewDay implements Job {
    public static int buffer;

    public void newDay() {
        if (buffer == 0) {
            return;
        }
        sendCalledDayLoopMessage();
        Weekday day = getWeekday();
        if (!(day == Weekday.SATURDAY || day == Weekday.SUNDAY)) {
            if (!alreadyPosted.equals(getDate())) {
                SlashCommandGeneral.postMessage(null);
                sendPostSuccess();
            } else {
                sendPostSkip();
            }
            String[] pendingDel = Config.getPendingDelSubj();
            if (pendingDel == null) {
                JdaMain.resetAcceptDelCommand();
                sendDelMessageSkip();
            } else {
                StringBuilder txt = new StringBuilder();
                OptionData faecher = new OptionData(OptionType.STRING, OptionSubjName, OptionSubjDescription, true);
                faecher.addChoice(ChoiceAllName, ChoiceAllValue);
                for (String s : pendingDel) {
                    faecher.addChoice(subjsConfig.getNameFromCode(s), s);
                    txt.append(subjsConfig.getNameFromCode(s)).append(newLine);
                }
                JdaSlashCommand acceptDelCommand = JdaMain.getCommandFromName(AcceptDelName);
                if (acceptDelCommand == null) {
                    return;
                }
                JdaMain.setCommand(acceptDelCommand, faecher);
                MessageEmbed embed = acceptDelHomework(txt.toString());
                for (String a : permissionsConfig.getAllIdsWithPermission(JdaPermission.ADMIN)) {
                    JdaMain.sendPrivateMessage(a, embed);
                }
                sendDelMessageSuccess();
            }
        }
        sendDoneDayLoopMessage();
        sendNextDayLoopScheduled(Time.getNextExecution());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        newDay();
    }
}
