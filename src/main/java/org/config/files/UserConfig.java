package org.config.files;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import org.config.ConfigFile;
import org.config.files.records.*;
import org.config.files.webuntis.WebUntisAPI;
import org.jda.slashcommands.JdaPermission;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.*;

import static org.config.files.webuntis.WebUntisAPI.getAllSubjects;
import static org.values.Global.*;
import static org.values.strings.Messages.subjectSelect;

public class UserConfig extends ConfigFile {

    private DiscordUser[] allUsers;

    public UserConfig(String fileName) {
        super(fileName);
        allUsers = getAllUsers();
    }
    // Schema: {Id}={permission}={subject, subject...}

    @Override
    protected String getTemplate() {
        return """
                # User automatisch generiert, nichts anfassen!
                """;
    }

    private DiscordUser[] getAllUsers() {
        String[] lines = getLines();
        ArrayList<DiscordUser> users = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(keySeperator);
            long id = Long.parseLong(split[0]);
            JdaPermission perm = JdaPermission.getFromInt(Integer.parseInt(split[1]));
            Subject[] subjects = null;
            if (split.length > 2) {
                subjects = Stream.of(split[2].split(commaRegex))
                        .map(WebUntisAPI::getSubjectFromName)
                        .toArray(Subject[]::new);
            }
            users.add(new DiscordUser(id, perm, subjects));
        }
        return users.toArray(new DiscordUser[0]);
    }

    private void setUser(long id, JdaPermission perm, @Nullable Subject[] subjects) {
        if (subjects == null) {
            setKey(String.valueOf(id), perm.getAsInt() + keySeperator, true);
        } else {
            setKey(String.valueOf(id), perm.getAsInt() + keySeperator + Arrays.stream(subjects).map(Subject::name).collect(Collectors.joining(commaRegex)), true);
        }
        reloadUsers();
    }

    public void setUserPermissions(User user, JdaPermission perms) {
        DiscordUser dcUser = getDiscordUserByUser(user);
        if (dcUser == null) {
            setUser(user.getIdLong(), perms, null);
        } else {
            setUser(dcUser.id(), perms, dcUser.subjects());
        }
    }

    public void setUserSubjects(User user, Subject[] available, Subject[] selected) {
        DiscordUser dcUser = getDiscordUserByUser(user);
        if (dcUser == null) {
            setUser(user.getIdLong(), JdaPermission.USER, selected);
        } else if (dcUser.subjects() == null) {
            setUser(dcUser.id(), dcUser.permissions(), selected);
        } else {
            ArrayList<Subject> selectedSubjects = new ArrayList<>();
            Arrays.stream(dcUser.subjects()).forEach(subject -> {
                boolean notInArray = true;
                for (Subject availableSubject : available) {
                    if (subject.id() == availableSubject.id()) {
                        notInArray = false;
                        break;
                    }
                }
                if (notInArray) {
                    selectedSubjects.add(subject);
                }
            });
            selectedSubjects.addAll(Arrays.asList(selected));
            setUser(dcUser.id(), dcUser.permissions(), selectedSubjects.toArray(new Subject[0]));
        }
    }

    public Subject[] filterUserSubjects(Subject[] in, User user) {
        if (getDiscordUserByUser(user) == null || getDiscordUserByUser(user).subjects() == null) {
            return in;
        }
        ArrayList<Subject> subjects = new ArrayList<>();
        for (Subject subject : in) {
            Arrays.stream(getDiscordUserByUser(user).subjects()).forEach(subject1 -> {
                if (subject.id() == subject1.id()) {
                    subjects.add(subject1);
                }
            });
        }
        return subjects.toArray(new Subject[0]);
    }

    public void reloadUsers() {
        allUsers = getAllUsers();
    }

    public DiscordUser getDiscordUserByUser(User user) {
        for (DiscordUser u : allUsers) {
            if (user.getIdLong() == u.id()) {
                return u;
            }
        }
        return null;
    }

    public JdaPermission getPermissions(User user) {
        DiscordUser dcUser = getDiscordUserByUser(user);
        if (dcUser == null) {
            return JdaPermission.USER;
        } else {
            return dcUser.permissions();
        }
    }

    public WebhookMessageCreateAction<Message> addSubjectSelectMessage(SlashCommandInteractionEvent event) {
        WebhookMessageCreateAction<Message> message = event.getHook().sendMessageEmbeds(subjectSelect());
        for (int i = 0; i < getAllSubjects().length; i += 25) {
            StringSelectMenu.Builder selectMenu = StringSelectMenu.create(subjectSelectMenuIdPrefix + ((i / 25) + 1)).setMinValues(0).setMaxValues(25);
            for (int j = i; j < i + 25 && j < getAllSubjects().length; j++) {
                String subj = getAllSubjects()[j].name();
                selectMenu.addOption(subj, subj);
            }
            if (getDiscordUserByUser(event.getUser()) != null && getDiscordUserByUser(event.getUser()).subjects() != null) {
                message.addActionRow(selectMenu.setDefaultValues(Arrays.stream(getDiscordUserByUser(event.getUser()).subjects()).map(Subject::name).toArray(String[]::new)).build());
            } else {
                message.addActionRow(selectMenu.build());
            }
        }
        return message;
    }
}
