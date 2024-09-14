package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.records.Subject;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.config.files.webuntis.WebUntisAPI.getSubjectFromName;
import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendResetHomework;
import static org.values.strings.Messages.resetHomework;

@SuppressWarnings("unused")
public class ResetHomeworkCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "reset";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Setzt eine Hausaufgabe zurück";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Löscht die Hausaufgabe in einem bestimmten Fach.";
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Subject subject = getSubjectFromName(getOptionByName(event, OptionSubjName));
        homeworkConfig.resetHomework(subject);
        replyEmbed(event, resetHomework(subject));
        sendResetHomework(event.getUser(), subject);
    }
}
