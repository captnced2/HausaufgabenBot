package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.postMessage;
import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.homeworkChannel;
import static org.values.strings.Console.sendPostSuccess;
import static org.values.strings.Messages.*;

public class PostHomeworkCommand implements JdaSlashCommand {

    @Override
    public boolean isInactive() {
        return false;
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
        if (!homeworkChannel.equals(event.getChannel().getName())) {
            replyEmbed(event, wrongChannel(), true);
            return;
        }
        String[] subj = mainConfig.getSubjsOnDay(getWeekday());
        if (subj == null) {
            replyEmbed(event, onlyUsableOnWeekdays(), true);
            return;
        }
        postMessage(event);
        alreadyPosted = getDate();
        sendPostSuccess(event.getUser());
    }
}
