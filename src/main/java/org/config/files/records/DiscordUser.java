package org.config.files.records;

import org.jda.slashcommands.JdaPermission;

public record DiscordUser(long id, JdaPermission permissions, Subject[] subjects) {
}
