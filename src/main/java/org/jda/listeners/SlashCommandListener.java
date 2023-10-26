package org.jda.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaPermission;
import org.jda.slashcommands.JdaSlashCommand;

import static org.jda.JdaMain.hasRequiredPermissions;
import static org.jda.JdaMain.replyEmbed;
import static org.values.strings.Messages.noPermissionsEmbed;
import static org.values.strings.Messages.somethingWentWrongEmbed;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        JdaSlashCommand command = JdaMain.getCommandFromName(event.getName());
        if (command != null) {
            JdaPermission requiredPermission = command.getRequiredPermission();
            if (hasRequiredPermissions(event.getUser(), requiredPermission)) {
                try {
                    command.execute(event);
                } catch (RuntimeException e) {
                    replyEmbed(event, somethingWentWrongEmbed(), true);
                }
            } else {
                replyEmbed(event, noPermissionsEmbed());
            }
        }
    }
}