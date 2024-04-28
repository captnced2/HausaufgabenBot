package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;
import org.time.Weekday;
import org.values.strings.Messages;

import java.util.Date;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.getHomeworkToDay;
import static org.time.Time.*;
import static org.values.strings.Console.sendRequestedTomorrowHomework;
import static org.values.strings.Messages.homeworkToDate;

@SuppressWarnings("unused")
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

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Gibt alle Hausaufgaben auf morgen aus.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Weekday day = getWeekday(1);
        Date date = getDate(1);
        boolean reply = false;
        if (day == Weekday.SATURDAY || day == Weekday.SUNDAY) {
            if (day == Weekday.SATURDAY) {
                date = getDate(3);
            } else {
                date = getDate(2);
            }
            day = Weekday.MONDAY;
            replyMessage(event, Messages.notSchooldayTomorrow, true);
            reply = true;
        }
        MessageEmbed embed = homeworkToDate(day, date, getHomeworkToDay(date));
        if (reply) {
            replyEmbedHook(event, embed);
        } else {
            replyEmbed(event, embed);
        }
        sendRequestedTomorrowHomework(event.getUser());
    }
}
