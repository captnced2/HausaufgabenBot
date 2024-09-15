package org.jda.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaSlashCommand;

import java.util.concurrent.TimeUnit;

import static org.config.files.webuntis.WebUntisAPI.getSubjectFromName;
import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.OptionSubjName;
import static org.main.Variables.userConfig;
import static org.values.strings.Console.sendError;
import static org.values.strings.Messages.*;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        JdaSlashCommand command = JdaMain.getCommandFromName(event.getName());
        if (command != null) {
            if (hasRequiredPermissions(event.getUser(), command.getRequiredPermission())) {
                if (!checkArguments(event)) {
                    return;
                }
                try {
                    command.execute(event);
                } catch (RuntimeException e) {
                    replyEmbed(event, somethingWentWrongEmbed(), true);
                    sendError(e.getMessage());
                }
                checkUserInit(event);
            } else {
                replyEmbed(event, noPermissionsEmbed(), true);
            }
        }
    }

    private void checkUserInit(SlashCommandInteractionEvent event) {
        if (userConfig.getDiscordUserByUser(event.getUser()) == null || userConfig.getDiscordUserByUser(event.getUser()).subjects() == null) {
            event.getHook().sendMessage(noSubjectsSelected).queue();
            Message m = replyCustom(event, userConfig.addSubjectSelectMessage(event));
            m.editMessageComponents().queueAfter(5, TimeUnit.MINUTES);
        }
    }

    private boolean checkArguments(SlashCommandInteractionEvent event) {
        OptionMapping subj = event.getOption(OptionSubjName);
        if (subj != null) {
            try {
                getSubjectFromName(subj.getAsString());
            } catch (RuntimeException e) {
                replyEmbed(event, noSubjectFound(subj.getAsString()), true);
                return false;
            }
        }
        return true;
    }
}