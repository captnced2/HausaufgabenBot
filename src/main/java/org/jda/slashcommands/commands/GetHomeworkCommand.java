package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.records.*;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.config.files.webuntis.WebUntisAPI.getSubjectFromName;
import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendRequestedHomework;
import static org.values.strings.Messages.*;

@SuppressWarnings("unused")
public class GetHomeworkCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "get";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt eine Hausaufgabe aus";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Gibt die Hausaufgabe in einem bestimmten Fach aus.";
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Subject subject = getSubjectFromName(getOptionByName(event, OptionSubjName));
        HomeworkInstance instance = homeworkConfig.getHomework(subject);
        String hw = instance.value();
        sendRequestedHomework(event.getUser(), subject);
        if (hw == null || hw.isEmpty()) {
            replyEmbed(event, noHomeworkFound(subject));
            return;
        }
        replyEmbed(event, homeworkFromDate(subject, instance.date(), JdaMain.getUserFromId(instance.userId()), hw));
    }
}
