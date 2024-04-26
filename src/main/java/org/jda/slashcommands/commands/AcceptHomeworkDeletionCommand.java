package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.config.Config;
import org.jda.JdaMain;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;
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

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Akzeptiert die Löschung von Hausaufgaben, die vom Bot vorgeschlagen wurden, weil es keine neue Hausaufgabe heute in dem Fach gab.";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.MOD;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String[] pendingDel = Config.getPendingDelSubj();
        if (pendingDel == null) {
            replyEmbed(event, noHomeworkToDelete(), true);
            return;
        }
        String subjCode = getOptionByName(event, OptionSubjName);
        if (subjCode.equals(ChoiceAllValue)) {
            StringBuilder txt = new StringBuilder();
            for (String del : pendingDel) {
                homeworkConfig.resetHomework(del);
                txt.append(subjsConfig.getNameFromCode(del)).append(newLine);
            }
            JdaMain.resetAcceptDelCommand();
            replyEmbed(event, deletedAllHomework(txt.toString()));
            sendDelAll(event.getUser());
            sendEmbedToChannelsByName(homeworkLogChannel, deletedAllHomeworkLog(txt.toString(), event.getUser()));
        } else {
            homeworkConfig.resetHomework(subjCode);
            String[] pendingDelSubj = Config.getPendingDelSubj();
            if (pendingDelSubj == null || pendingDelSubj.length == 0) {
                JdaMain.resetAcceptDelCommand();
            }
            replyEmbed(event, deletedHomework(subjCode));
            sendDelSubj(event.getUser(), subjCode);
            sendEmbedToChannelsByName(homeworkLogChannel, deletedHomeworkLog(subjCode, event.getUser()));
        }
    }
}
