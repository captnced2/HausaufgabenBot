package org.jda.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;

public interface JdaSlashCommand {

    default boolean isInactive() {
        return false;
    }

    @Nonnull
    String getName();

    @Nonnull
    String getDescription();

    default OptionData[] getOptions() {
        return null;
    }

    void execute(SlashCommandInteractionEvent event);
}
