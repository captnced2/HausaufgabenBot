package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendRequestedHomework;
import static org.values.strings.Messages.*;

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
        String subjCode = getOptionByName(event, OptionSubjName);
        String hw = homeworkConfig.getHomework(subjCode);
        sendRequestedHomework(event.getUser(), subjCode);
        if (hw == null || hw.isEmpty()) {
            replyEmbed(event, noHomeworkFound(subjCode));
            return;
        }
        replyEmbed(event, homeworkFromDate(subjCode, homeworkConfig.getHomeworkDate(subjCode), hw));
    }
}
