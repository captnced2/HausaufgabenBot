package org.jda.listeners;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.config.files.records.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.config.files.webuntis.WebUntisAPI.getAllSubjects;

public class CommandAutoCompleteListener extends ListenerAdapter {
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        if (event.getFocusedOption().getName().equals("fach")) {
            ArrayList<Subject> subjects = new ArrayList<>(List.of(getAllSubjects()));
            List<Command.Choice> options = subjects.stream()
                    .filter(word -> word.name().toLowerCase().startsWith(event.getFocusedOption().getValue().toLowerCase()))
                    .map(word -> new Command.Choice(word.name(), word.name()))
                    .toList();
            if (options.size() > 25) {
                options = options.subList(0, 24);
            }
            event.replyChoices(options).queue();
        }
    }
}
