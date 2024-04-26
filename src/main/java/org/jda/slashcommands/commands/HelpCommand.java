package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.jda.JdaMain.getAllowedCommands;

public class HelpCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "help";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gibt alle verfügbaren Commands mit erklärung aus";
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {
        ArrayList<JdaSlashCommand> allowedCommands = getAllowedCommands(event.getUser());
        ArrayList<String> helpDescriptions = new ArrayList<>();
        for (JdaSlashCommand command : allowedCommands) {
            helpDescriptions.add(command.getDescription());
        }
    }
}
