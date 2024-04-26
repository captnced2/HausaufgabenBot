package org.jda.slashcommands.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jda.slashcommands.*;
import org.jetbrains.annotations.NotNull;

import java.text.*;
import java.util.*;

import static org.jda.JdaMain.replyEmbed;
import static org.jda.slashcommands.SlashCommandGeneral.*;
import static org.main.Variables.holidayConfig;
import static org.values.Global.dayMonthYearPattern;
import static org.values.strings.Console.sendSetHolidays;
import static org.values.strings.Errors.optionNotADate;
import static org.values.strings.Messages.setHolidays;

public class SetHolidaysCommand implements JdaSlashCommand {
    @NotNull
    @Override
    public String getName() {
        return "setholidays";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Setzt Ferien fest";
    }

    @NotNull
    @Override
    public String getHelpDescription() {
        return "Legt Ferien fest, in denen der Bot inaktiv ist. Nicht mehr änderbar!";
    }

    @Override
    public JdaPermission getRequiredPermission() {
        return JdaPermission.ADMIN;
    }

    @Override
    public List<OptionData> getOptions() {
        return buildOptionData(new OptionData(OptionType.STRING, OptionDateFromName, OptionDateDescription, true), new OptionData(OptionType.STRING, OptionDateToName, OptionDateDescription, true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String from = getOptionByName(event, OptionDateFromName);
        String to = getOptionByName(event, OptionDateToName);
        SimpleDateFormat formatter = new SimpleDateFormat(dayMonthYearPattern);
        try {
            Date dateFrom = formatter.parse(from);
            Date dateTo = formatter.parse(to);
            holidayConfig.setHolidaysPeriod(dateFrom, dateTo);
        } catch (ParseException e) {
            throw new RuntimeException(optionNotADate(event.getUser(), this.getName()));
        }
        replyEmbed(event, setHolidays(from, to));
        sendSetHolidays(event.getUser());
    }
}
