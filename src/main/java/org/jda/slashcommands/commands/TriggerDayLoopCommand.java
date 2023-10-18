package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;
import org.time.Time;

import static org.jda.JdaMain.replyEmbed;
import static org.values.strings.Messages.triggeredDayLoopEmbed;

public class TriggerDayLoopCommand implements JdaSlashCommand {

    @Override
    public boolean isInactive() {
        return true;
    }

    @NotNull
    @Override
    public String getName() {
        return "triggerloop";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Führt den Tages Loop aus";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.OWNER;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Time.newDay();
        replyEmbed(event, triggeredDayLoopEmbed(), true);
    }
}
