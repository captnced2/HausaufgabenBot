package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;
import org.time.Weekday;

import java.util.Date;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.getHomeworkToDayByUser;
import static org.time.Time.*;
import static org.values.strings.Console.sendRequestedTodayHomework;
import static org.values.strings.Messages.*;

@SuppressWarnings("unused")
public class GettodayCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "gettoday";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt die Hausaufgaben von Heute aus";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Gibt alle Hausaufgaben auf heute aus.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Weekday day = getWeekday();
        Date date = getDate();
        if (isWeekend()) {
            replyMessage(event, notSchooldayToday, true);
        } else {
            replyEmbed(event, homeworkToDate(day, date, getHomeworkToDayByUser(date, event.getUser())));
        }
        sendRequestedTodayHomework(event.getUser());
    }
}
