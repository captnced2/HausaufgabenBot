package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.*;
import static org.values.strings.Console.sendUsedHelpCommand;
import static org.values.strings.Messages.helpCommand;

public class HelpCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "help";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt alle Commands mit Beschreibungen aus";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Zeigt dieses Fenster an";
    }

    public void execute(SlashCommandInteractionEvent event) {
        replyEmbed(event, helpCommand(event.getUser(), getAllHelpDescriptions(event.getUser())));
        sendUsedHelpCommand(event.getUser());
    }
}
