package org.time;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.Config;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaPermission;
import org.jda.slashcommands.JdaSlashCommand;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.permissionsConfig;
import static org.main.Variables.subjsConfig;
import static org.time.Time.isWeekend;
import static org.values.Global.homeworkChannel;
import static org.values.Global.newLine;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.acceptDelHomework;
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
        for (Guild guild : getAllGuilds()) {
            for (TextChannel channel : guild.getTextChannels()) {
                if (channel.getName().equals(homeworkChannel)) {
                    sendEmbed(channel, postMessageForToday());
                    String ping = getPingRolePing(guild);
                    if (ping != null) {
                        sendWithDelay(channel, ping, 1);
                    }
                    break;
                }
            }
        }
        sendPostSuccess();
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
        sendDoneDayLoopMessage();
        sendNextDayLoopScheduled(Time.getNextExecution());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        newDay();
    }
}
