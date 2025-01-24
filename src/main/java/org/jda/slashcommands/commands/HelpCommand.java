package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.jda.JdaMain.*;
import static org.values.strings.Console.sendAskedForHelp;
import static org.values.strings.Messages.helpEmbed;

@SuppressWarnings("unused")
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

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Zeigt diese Infos.";
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {
        ArrayList<JdaSlashCommand> allowedCommands = getAllowedCommands(event.getUser());
        ArrayList<String> commandHelpDescriptions = new ArrayList<>();
        for (JdaSlashCommand command : allowedCommands) {
            commandHelpDescriptions.add(command.getName() + ": " + command.getHelpDescription());
        }
        commandHelpDescriptions.sort(String::compareToIgnoreCase);
        StringBuilder helpMessage = new StringBuilder();
        for (int i = 0; i < commandHelpDescriptions.size(); i++) {
            helpMessage.append(i).append(". ").append(commandHelpDescriptions.get(i)).append("\n");
        }
        replyEmbed(event, helpEmbed(getUserPermissions(event.getUser()), helpMessage.toString()), true);
        sendAskedForHelp(event);
    }
}
