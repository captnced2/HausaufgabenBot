package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendAddedCancelledSubj;
import static org.values.strings.Messages.addedCancelledSubj;

public class SetLessonsCancelledCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "setcancelled";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Setzt ausfallende Fächer fest";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Setzt Fächer als ausfallend fest. Diese Fächer werden an dem bestimmten Tag nicht berücksichtigt. Nicht mehr änderbar!";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(subjOption, dateOption);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String subjCode = getOptionByName(event, OptionSubjName);
        String date = getOptionByName(event, OptionDateName);
        cancelledConfig.addCancelledSubj(subjCode, date);
        replyEmbed(event, addedCancelledSubj(subjCode, date));
        sendAddedCancelledSubj(event.getUser(), subjCode, date);
    }
}
