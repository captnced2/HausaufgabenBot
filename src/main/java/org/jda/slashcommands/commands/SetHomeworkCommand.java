package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.files.records.Subject;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.config.files.webuntis.WebUntisAPI.getSubjectFromName;
import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendSetHomework;
import static org.values.strings.Messages.changedHomework;

@SuppressWarnings("unused")
public class SetHomeworkCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "set";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Setzt eine Hausaufgabe fest";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Legt die Hausaufgabe in einem bestimmten Fach fest.";
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption, new OptionData(OptionType.STRING, OptionHomeworkName, OptionHomeworkDescription, true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Subject subject = getSubjectFromName(getOptionByName(event, OptionSubjName));
        String hw = getOptionByName(event, OptionHomeworkName);
        if (hw.equals("null")) {
            hw = "";
        }
        homeworkConfig.setHomework(subject, hw, event.getUser());
        replyEmbed(event, changedHomework(subject, hw));
        sendSetHomework(event, subject, hw);
    }
}
