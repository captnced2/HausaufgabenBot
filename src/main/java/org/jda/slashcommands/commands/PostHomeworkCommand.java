package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;
import org.time.Time;

import static org.jda.JdaMain.*;
import static org.main.Variables.holidayConfig;
import static org.time.Time.isWeekend;
import static org.values.Global.homeworkChannel;
import static org.values.strings.Console.sendPostSuccess;
import static org.values.strings.Messages.*;

public class PostHomeworkCommand implements JdaSlashCommand {

    @Override
    public boolean isInactive() {
        return true;
    }

    @NotNull
    @Override
    public String getName() {
        return "post";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt die Hausaufgaben von heute aus";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (!event.getChannel().getName().equals(homeworkChannel) || guild == null) {
            replyEmbed(event, wrongChannel(), true);
            return;
        }
        if (isWeekend() || holidayConfig.isHolidayDay(Time.getDate())) {
            replyEmbed(event, notSchoolday(), true);
            return;
        }
        replyEmbed(event, postMessageForToday());
        String ping = getPingRolePing(guild);
        if (ping != null) {
            sendWithDelay(event.getChannel(), ping, 1);
        }
        sendPostSuccess(event.getUser());
    }
}
