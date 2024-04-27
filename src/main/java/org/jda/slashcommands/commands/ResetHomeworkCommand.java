package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    public JdaPermission getRequiredPermission() {
        return JdaPermission.MOD;
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String subjCode = getOptionByName(event, OptionSubjName);
        homeworkConfig.resetHomework(subjCode);
        replyEmbed(event, resetHomework(subjCode));
        sendResetHomework(event.getUser(), subjCode);
    }
}
