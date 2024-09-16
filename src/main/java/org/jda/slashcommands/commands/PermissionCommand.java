package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.jda.JdaMain.*;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.values.strings.Console.sendSetPermissions;
import static org.values.strings.Messages.*;

@SuppressWarnings("unused")
public class PermissionCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "setperms";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Setzt Berechtigungen von Usern";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "-";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(userOption, permissionOption);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        User user = getUserOptionByName(event, OptionUserName);
        JdaPermission permission = JdaPermission.valueOf(getOptionByName(event, OptionPermissionName));
        if (!hasRequiredPermissions(event.getUser(), permission)) {
            replyEmbed(event, noPermissionsEmbed(), true);
            return;
        }
        if (getUserPermissions(user).getAsInt() > getUserPermissions(event.getUser()).getAsInt()) {
            replyEmbed(event, noPermissionsEmbed(), true);
            return;
        }
        if (event.getUser().getIdLong() == user.getIdLong()) {
            replyEmbed(event, noPermissionsEmbed(), true);
            return;
        }

        userConfig.setUserPermissions(user, permission);
        replyEmbed(event, permissionsSet(user, permission), true);
        sendSetPermissions(event.getUser(), user, permission);
    }
}
