package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;
import org.main.Main;

import static org.jda.JdaMain.replyEmbed;
import static org.values.strings.Messages.shutdownEmbed;

public class ShutdownCommand implements JdaSlashCommand {

    @NotNull
    @Override
    public String getName() {
        return "shutdown";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Fährt den Bot herunter";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Fährt den Bot herunter. D:";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.OWNER;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        replyEmbed(event, shutdownEmbed(), true);
        Main.shutdown();
    }
}
