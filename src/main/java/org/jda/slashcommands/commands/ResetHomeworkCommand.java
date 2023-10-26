package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.OptionSubjName;
import static org.main.Variables.*;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.*;

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

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.MOD;
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
        homeworkConfig.resetHomework(subjCode);
        replyEmbed(event, resetHomework(subjCode));
        sendResetHomework(event.getUser(), subjCode);
    }
}
