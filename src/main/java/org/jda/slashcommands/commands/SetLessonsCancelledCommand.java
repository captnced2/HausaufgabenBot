package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.config.Config.addCancelledHw;
import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.subjOption;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.*;

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

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {subjOption, new OptionData(OptionType.STRING, OptionDateName, OptionDateDescription, true)};
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping subjCodeOption = event.getOption(OptionSubjName);
        OptionMapping dateOption = event.getOption(OptionDateName);
        if (subjCodeOption == null || dateOption == null) {
            sendCommandError(event.getUser(), this.getName());
            replyEmbed(event, somethingWentWrongEmbed(), true);
            return;
        }
        String subjCode = subjCodeOption.getAsString();
        String date = dateOption.getAsString();
        addCancelledHw(subjCode, date);
        replyEmbed(event, addedCancelledSubj(subjCode, date));
        sendAddedCancelledSubj(event.getUser(), subjCode, date);
    }
}
