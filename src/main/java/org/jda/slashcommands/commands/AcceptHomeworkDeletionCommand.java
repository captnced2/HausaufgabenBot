package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.config.Config;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaPermission;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.JdaMain.sendEmbedToChannelsByName;
import static org.jda.slashcommands.SlashCommandGeneral.ChoiceAllValue;
import static org.jda.slashcommands.SlashCommandGeneral.OptionSubjName;
import static org.main.Variables.homeworkConfig;
import static org.main.Variables.subjsConfig;
import static org.values.Global.homeworkLogChannel;
import static org.values.Global.newLine;
import static org.values.strings.Console.sendDelAll;
import static org.values.strings.Console.sendDelSubj;
import static org.values.strings.Messages.*;

public class AcceptHomeworkDeletionCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "accept";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Akzeptiert die Löschung von ungeänderten Hausaufgaben";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String[] pendingDel = Config.getPendingDelSubj();
        if (pendingDel == null) {
            replyEmbed(event, noHomeworkToDelete(), true);
            return;
        }
        OptionMapping subjCodeOption = event.getOption(OptionSubjName);
        if (subjCodeOption == null) {
            replyEmbed(event, noHomeworkToDelete(), true);
            return;
        }
        String subjCode = subjCodeOption.getAsString();
        if (subjCode.equals(ChoiceAllValue)) {
            StringBuilder txt = new StringBuilder();
            for (String del : pendingDel) {
                homeworkConfig.resetHomework(del);
                txt.append(subjsConfig.getNameFromCode(del)).append(newLine);
            }
            JdaMain.resetAcceptDelCommand();
            replyEmbed(event, deletedAllHomework(txt.toString()));
            sendDelAll(event.getUser());
            sendEmbedToChannelsByName(homeworkLogChannel, addedAllDelHomeworkForLog(txt.toString(), event.getUser().getEffectiveName()));

        } else {
            homeworkConfig.resetHomework(subjCode);
            String[] pendingDelSubj = Config.getPendingDelSubj();
            if (pendingDelSubj == null || pendingDelSubj.length == 0) {
                JdaMain.resetAcceptDelCommand();
            }
            replyEmbed(event, deletedHomework(subjCode));
            sendDelSubj(event.getUser(), subjCode);
            sendEmbedToChannelsByName(homeworkLogChannel, addedPartDelHomeworkForLog(subjsConfig.getNameFromCode(subjCode), event.getUser().getEffectiveName()));
        }
    }
}
