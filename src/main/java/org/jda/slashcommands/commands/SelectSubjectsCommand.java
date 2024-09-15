package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jda.slashcommands.JdaSlashCommand;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static org.jda.JdaMain.replyCustom;
import static org.main.Variables.userConfig;

@SuppressWarnings("unused")
public class SelectSubjectsCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "selectsubjects";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Eigene Fächer auswählen";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Sendet eine Nachricht, mit der man seine persönlichen Fächer festlegen kann.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Message m = replyCustom(event, userConfig.addSubjectSelectMessage(event));
        m.editMessageComponents().queueAfter(5, TimeUnit.MINUTES);
    }
}
