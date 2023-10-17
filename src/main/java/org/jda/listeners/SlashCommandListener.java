package org.jda.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jda.JdaMain;
import org.jda.slashcommands.JdaSlashCommand;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        JdaSlashCommand command = JdaMain.getCommandFromName(event.getName());
        if (command != null) {
            command.execute(event);
        }
    }
}