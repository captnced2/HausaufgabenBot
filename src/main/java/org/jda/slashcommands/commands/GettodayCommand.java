package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;
import org.time.Weekday;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.getHomeworkToDay;
import static org.time.Time.*;
import static org.values.strings.Console.sendRequestedTodayHomework;
import static org.values.strings.Messages.*;

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

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Weekday day = getWeekday();
        String date = getDate();
        if (isWeekend()) {
            replyMessage(event, notSchooldayToday, true);
        } else {
            replyEmbed(event, homeworkToDate(day, date, getHomeworkToDay(day)));
        }
        sendRequestedTodayHomework(event.getUser());
    }
}
