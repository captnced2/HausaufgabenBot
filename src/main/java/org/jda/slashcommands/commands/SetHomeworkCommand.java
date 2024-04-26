package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.*;

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
    public JdaPermission getRequiredPermission() {
        return JdaPermission.MOD;
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption, new OptionData(OptionType.STRING, OptionHomeworkName, OptionHomeworkDescription, true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String subjCode = getOptionByName(event, OptionSubjName);
        String ha = getOptionByName(event, OptionHomeworkName);
        if (ha.equals("null")) {
            ha = "";
        }
        boolean New = homeworkConfig.setHomework(subjCode, ha);
        if (New) {
            replyEmbed(event, addedHomework(subjCode, ha));
            sendAddedHomework(event.getUser(), subjCode, ha);
        } else {
            replyEmbed(event, changedHomework(subjCode, ha));
            sendSetHomework(event.getUser(), subjCode, ha);
        }
    }
}
