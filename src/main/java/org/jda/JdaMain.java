package org.jda;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.OkHttpClient;
import org.config.files.subjects.Subject;
import org.jda.listeners.*;
import org.jda.slashcommands.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.time.Weekday;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.*;
import static org.time.Time.*;
import static org.values.Global.*;
import static org.values.strings.Console.*;
import static org.values.strings.Messages.defaultActivity;

public class JdaMain {

    private static JDA Jda;
    private static ArrayList<JdaSlashCommand> slashCommands;

    public static void initJda() {
        sendJdaStartup();
        createJda();
        defineOptions();
        getAllCommands();
        setPfp();
        addCommands();
        sendJdaStartupComplete();
    }

    private static void createJda() {
        int retries = 100;
        int retryCount = 0;
        while (retryCount <= retries) {
            try {
                URI url = new URI("https://www.google.com");
                URLConnection connection = url.toURL().openConnection();
                connection.connect();
                connection.getInputStream().close();
                break;
            } catch (URISyntaxException | IOException e) {
                retryCount++;
                sendRetryingInternetConnection(retries - retryCount);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        Jda = JDABuilder.createDefault(mainConfig.getToken())
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new SlashCommandListener())
                .addEventListeners(new UserOnlineListener())
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        setDefaultActivity();
        awaitInit();
    }

    private static void defineOptions() {
        subjOption = new OptionData(OptionType.STRING, OptionSubjName, OptionSubjDescription, true);
        for (Subject subject : subjsConfig.getAllSubjects()) {
            subjOption.addChoice(subject.name(), subject.code());
        }
        updateDateOption();
    }

    public static void updateDateOption() {
        int shift = 0;
        ArrayList<String> dates = new ArrayList<>();
        if (isWeekend()) {
            if (getWeekday() == Weekday.SATURDAY) {
                shift = 2;
            } else {
                shift = 1;
            }
        }
        while (getWeekday(shift) != Weekday.SATURDAY) {
            dates.add(getDateString(shift));
            shift++;
        }
        dateOption = new OptionData(OptionType.STRING, OptionDateName, OptionDateDescription, true);
        for (String d : dates) {
            dateOption.addChoice(d, d);
        }
    }

    private static void setPfp() {
        setProfilePicture(mainConfig.getStandardProfilePictureFile());
    }

    private static void getAllCommands() {
        slashCommands = new ArrayList<>();
        commandsCount = 0;
        Reflections reflections = new Reflections(commandPackageName, Scanners.SubTypes);
        Set<Class<? extends JdaSlashCommand>> allCommands = reflections.getSubTypesOf(JdaSlashCommand.class);
        for (Class<?> command : allCommands) {
            try {
                JdaSlashCommand instance = (JdaSlashCommand) command.getDeclaredConstructor().newInstance();
                if (!instance.isInactive()) {
                    slashCommands.add(instance);
                    commandsCount++;
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                commandsCount++;
                sendCommandRegisterError(command.getName());
            }
        }
    }

    private static void addCommands() {
        int registeredCommands = 0;
        StringBuilder commandList = new StringBuilder();
        List<Command> activeCommands = Jda.retrieveCommands().complete();
        for (JdaSlashCommand command : slashCommands) {
            List<OptionData> commandOptions = command.getOptions();
            String name = command.getName();
            if (commandOptions != null) {
                SlashCommandData commandData = Commands.slash(name, command.getDescription());
                for (OptionData data : commandOptions) {
                    commandData.addOptions(data);
                }
                setCommand(commandData);
            } else {
                setCommand(command);
            }
            activeCommands.removeIf(c -> c.getName().equals(name));
            if (registeredCommands == 0) {
                commandList.append(name);
            } else {
                commandList.append(commaRegex).append(name);
            }
            registeredCommands++;
        }
        sendRegisteredCommands(registeredCommands, commandsCount, commandList.toString());
        if (!activeCommands.isEmpty()) {
            int removedCommands = 0;
            for (Command c : activeCommands) {
                c.delete().queue();
                removedCommands++;
            }
            sendDeletedCommands(removedCommands);
        }
    }

    public static JdaSlashCommand getCommandFromName(String name) {
        for (JdaSlashCommand command : slashCommands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public static void refreshCommand(JdaSlashCommand command) {
        List<OptionData> commandOptions = command.getOptions();
        if (commandOptions != null) {
            SlashCommandData commandData = Commands.slash(command.getName(), command.getDescription());
            for (OptionData data : commandOptions) {
                commandData.addOptions(data);
            }
            setCommand(commandData);
        } else {
            setCommand(command);
        }
    }

    public static void setCommand(String cmd, String desc) {
        Jda.upsertCommand(cmd, desc).queue();
    }

    public static void setCommand(JdaSlashCommand command) {
        setCommand(command.getName(), command.getDescription());
    }

    @SuppressWarnings("unused")
    public static void setCommand(JdaSlashCommand command, OptionData options) {
        setCommand(command.getName(), command.getDescription(), options);
    }

    public static void setCommand(CommandData commandData) {
        Jda.upsertCommand(commandData).queue();
    }

    public static void setCommand(String command, String description, OptionData options) {
        Jda.upsertCommand(command, description).addOptions(options).queue();
    }

    @SuppressWarnings("unused")
    public static void sendPrivateMessage(String id, MessageEmbed embed) {
        User u = Jda.getUserById(id);
        if (!(u == null)) {
            u.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed)).queue();
        }
    }

    public static void setCustomActivity(String activity) {
        Jda.getPresence().setActivity(Activity.customStatus(activity));
    }

    public static void setDefaultActivity() {
        setCustomActivity(defaultActivity);
    }

    public static List<Guild> getAllGuilds() {
        return Jda.getGuilds();
    }

    public static String getPingRolePing(Guild guild) {
        for (Role role : guild.getRoles()) {
            if (role.getName().equals(pingRoleName)) {
                return "<@&" + role.getId() + ">";
            }
        }
        return null;
    }

    public static List<TextChannel> getAllChannelsFromName(String name) {
        ArrayList<TextChannel> channels = new ArrayList<>();
        for (Guild guild : getAllGuilds()) {
            for (TextChannel channel : guild.getTextChannels()) {
                if (channel.getName().equals(name)) {
                    channels.add(channel);
                }
            }
        }
        return channels;
    }

    public static ArrayList<JdaSlashCommand> getAllowedCommands(User user) {
        ArrayList<JdaSlashCommand> allowedCommands = new ArrayList<>();
        for (JdaSlashCommand command : slashCommands) {
            if (hasRequiredPermissions(user, command.getRequiredPermission())) {
                allowedCommands.add(command);
            }
        }
        return allowedCommands;
    }

    public static void sendEmbedToChannelByNameWithPing(String channelName, MessageEmbed embed) {
        for (TextChannel channel : getAllChannelsFromName(channelName)) {
            sendEmbed(channel, embed);
            String ping = getPingRolePing(channel.getGuild());
            if (ping != null) {
                sendWithDelay(channel, ping, 1);
            }
        }
    }

    public static void replyMessage(SlashCommandInteractionEvent event, String message, boolean ephemeral) {
        event.reply(message).setEphemeral(ephemeral).queue();
    }

    public static void replyEmbed(SlashCommandInteractionEvent event, MessageEmbed embed) {
        replyEmbed(event, embed, false);
    }

    public static void replyEmbed(SlashCommandInteractionEvent event, MessageEmbed embed, boolean ephemeral) {
        event.replyEmbeds(embed).setEphemeral(ephemeral).queue();
    }

    public static void replyEmbedHook(SlashCommandInteractionEvent event, MessageEmbed embed) {
        event.getHook().getInteraction().getMessageChannel().sendMessageEmbeds(embed).queue();
    }

    public static void sendEmbed(TextChannel channel, MessageEmbed embed) {
        channel.sendMessageEmbeds(embed).queue();
    }

    public static void sendWithDelay(TextChannel channel, String text, int delay) {
        channel.sendMessage(text).queueAfter(delay, TimeUnit.SECONDS);
    }

    public static void sendWithDelay(MessageChannelUnion channel, String text, int delay) {
        channel.sendMessage(text).queueAfter(delay, TimeUnit.SECONDS);
    }

    public static void setProfilePicture(File iconPath) {
        try {
            Jda.getSelfUser().getManager().setAvatar(Icon.from(iconPath)).queue();
        } catch (IOException ignored) {
        }
    }

    public static JdaPermission getUserPermissions(User user) {
        return permissionsConfig.getPermissionsById(user.getId());
    }

    public static boolean hasRequiredPermissions(User user, JdaPermission requiredPermission) {
        return getUserPermissions(user).getAsInt() >= requiredPermission.getAsInt();
    }

    public static void awaitInit() {
        try {
            Jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setOffline() {
        Jda.getPresence().setActivity(null);
        Jda.getPresence().setStatus(OnlineStatus.OFFLINE);
    }

    public static void shutdown() {
        setOffline();
        try {
            TimeUnit.SECONDS.sleep(1);
            Jda.shutdown();
            OkHttpClient client = Jda.getHttpClient();
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
            if (!Jda.awaitShutdown(5, TimeUnit.SECONDS)) {
                sendJdaForceShutdownMessage();
                Jda.shutdownNow();
            }
        } catch (InterruptedException ignored) {
        }
    }
}
