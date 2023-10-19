package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.config.Config;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.alreadyPosted;
import static org.time.Time.*;
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
        if (!checkGuild(event)) {
            replyEmbed(event, wrongServer(), true);
            return;
        }
        if (!checkChannel(event)) {
            replyEmbed(event, wrongChannel(), true);
            return;
        }
        String[] subj = Config.getDaySubj(getWeekday());
        if (subj == null) {
            replyEmbed(event, onlyUsableOnWeekdays(), true);
            return;
        }
        postMessage(event);
        alreadyPosted = getDate();
        sendPostSuccess(event.getUser());
    }
}
