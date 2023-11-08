package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import static org.jda.JdaMain.getHelpDescriptionCommands;
import static org.jda.JdaMain.replyEmbed;
import static org.values.strings.Console.sendHelpCommandUse;
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
        return "Listet alle Befehle auf";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Wenn du Hilfe gebraucht hast, yippie!!! You found the command :)";
    }

    public void execute(SlashCommandInteractionEvent event) {
        replyEmbed(event, helpCommand(event.getUser(), getHelpDescriptionCommands(event.getUser())));
        sendHelpCommandUse(event.getUser());
    }
}
