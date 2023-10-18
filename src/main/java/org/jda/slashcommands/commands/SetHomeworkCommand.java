package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.config.Config;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Varibles.subjOption;
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

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {subjOption, new OptionData(OptionType.STRING, OptionHomeworkName, OptionHomeworkDescription, true)};
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.MOD;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping subjCodeOption = event.getOption(OptionSubjName);
        OptionMapping haOption = event.getOption(OptionHomeworkName);
        if (subjCodeOption == null || haOption == null) {
            sendCommandError(event.getUser(), this.getName());
            replyEmbed(event, somethingWentWrongEmbed(), true);
            return;
        }
        String subjCode = subjCodeOption.getAsString();
        String ha = haOption.getAsString();
        if (ha.equals("null")) {
            ha = "";
        }
        boolean New = Config.setHomework(subjCode, ha);
        if (New) {
            replyEmbed(event, addedHomework(subjCode, ha));
            sendAddedHomework(event.getUser(), subjCode, ha);
        } else {
            replyEmbed(event, changedHomework(subjCode, ha));
            sendSetHomework(event.getUser(), subjCode, ha);
        }
    }
}
