package org.jda.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaSlashCommand;

import static org.jda.JdaMain.*;
import static org.values.strings.Console.sendError;
import static org.values.strings.Messages.*;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        JdaSlashCommand command = JdaMain.getCommandFromName(event.getName());
        if (command != null) {
            if (hasRequiredPermissions(event.getUser(), command.getRequiredPermission())) {
                try {
                    command.execute(event);
                } catch (RuntimeException e) {
                    replyEmbed(event, somethingWentWrongEmbed(), true);
                    sendError(e.getMessage());
                }
            } else {
                replyEmbed(event, noPermissionsEmbed());
            }
        }
    }
}