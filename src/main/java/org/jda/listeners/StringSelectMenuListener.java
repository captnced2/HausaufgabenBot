package org.jda.listeners;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.config.files.records.Subject;
import org.config.files.webuntis.WebUntisAPI;
import org.jetbrains.annotations.NotNull;

import static org.config.files.webuntis.WebUntisAPI.getSubjectFromName;
import static org.jda.JdaMain.replyEmbed;
import static org.main.Variables.userConfig;
import static org.values.Global.subjectSelectMenuIdPrefix;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.*;

public class StringSelectMenuListener extends ListenerAdapter {
    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        try {
            if (event.getMessage().getInteraction() == null) {
                return;
            }
            if (event.getMessage().getInteraction().getUser() != event.getUser()) {
                replyEmbed(event, notYourMessage(), true);
                return;
            }
            if (event.getComponentId().contains(subjectSelectMenuIdPrefix)) {
                Subject[] available = event.getComponent().getOptions().stream().map(option -> getSubjectFromName(option.getValue())).toArray(Subject[]::new);
                Subject[] selected = event.getValues().stream().map(WebUntisAPI::getSubjectFromName).toArray(Subject[]::new);
                userConfig.setUserSubjects(event.getUser(), available, selected);
                replyEmbed(event, savedChanges(), true);
                sendChangedSubjects(event.getUser());
            }
        } catch (Exception e) {
            replyEmbed(event, somethingWentWrongEmbed(), true);
            sendError(e.getMessage());
        }
    }
}
