package org.jda.slashcommands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;
import java.util.List;

import static org.values.strings.Errors.noOptionValue;

public interface JdaSlashCommand {

    default boolean isInactive() {
        return false;
    }

    @Nonnull
    String getName();

    @Nonnull
    String getDescription();

    @Nonnull
    String getHelpDescription();

    default JdaPermission getRequiredPermission() {
        return JdaPermission.USER;
    }

    default List<OptionData> getOptions() {
        return null;
    }

    default String getOptionByName(SlashCommandInteractionEvent event, String name) {
        OptionMapping optionMapping = event.getOption(name);
        if (optionMapping == null) {
            throw new RuntimeException(noOptionValue(event.getUser(), this.getName()));
        }
        return optionMapping.getAsString();
    }

    default User getUserOptionByName(SlashCommandInteractionEvent event, String name) {
        OptionMapping optionMapping = event.getOption(name);
        if (optionMapping == null) {
            throw new RuntimeException(noOptionValue(event.getUser(), this.getName()));
        }
        return optionMapping.getAsUser();
    }

    void execute(SlashCommandInteractionEvent event);
}
