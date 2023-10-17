package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;
import org.values.strings.Messages;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.getHomeworkToDay;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Console.sendRequestedTomorrowHomework;
import static org.values.strings.Messages.homeworkToDate;

public class GettomorrowCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "gettomorrow";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt die Hausaufgaben von morgen aus";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String day = getWeekday(1);
        String date = getDate(1);
        boolean reply = false;
        if (day.equals(saturday) || day.equals(sunday)) {
            if (day.equals(saturday)) {
                date = getDate(3);
            } else {
                date = getDate(2);
            }
            day = monday;
            replyMessage(event, Messages.notSchooldayTomorrow, true);
            reply = true;
        }
        if (reply) {
            replyEmbedHook(event, homeworkToDate(day, date, getHomeworkToDay(day)));
        } else {
            replyEmbed(event, homeworkToDate(day, date, getHomeworkToDay(day)));
        }
        sendRequestedTomorrowHomework(event.getUser());
    }
}
