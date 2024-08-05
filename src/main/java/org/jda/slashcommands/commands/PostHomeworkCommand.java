package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.time.Time.isWeekend;
import static org.values.Global.homeworkChannel;
import static org.values.strings.Console.sendPostSuccess;
import static org.values.strings.Messages.*;

@SuppressWarnings("unused")
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

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Lässt den Bot alle Hausaufgaben ausgeben, die heute aufgegeben wurden.";
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
        if (isWeekend()) {
            replyEmbed(event, notSchoolday(), true);
            return;
        }
        replyEmbed(event, postMessageForToday());
        sendPostSuccess(event.getUser());
    }
}
