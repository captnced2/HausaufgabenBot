package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.Config;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.OptionSubjName;
import static org.main.Variables.subjOption;
import static org.values.strings.Console.*;
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

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {subjOption};
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping subjCodeOption = event.getOption(OptionSubjName);
        if (subjCodeOption == null) {
            sendCommandError(event.getUser(), this.getName());
            replyEmbed(event, somethingWentWrongEmbed(), true);
            return;
        }
        String subjCode = subjCodeOption.getAsString();
        String hw = Config.getHomework(subjCode);
        if (hw == null || hw.isEmpty()) {
            replyEmbed(event, noHomeworkFound(subjCode));
            return;
        }
        replyEmbed(event, homeworkFromDate(subjCode, Config.getHomeworkDate(subjCode), hw));
        sendRequestedHomework(event.getUser(), subjCode);
    }
}
