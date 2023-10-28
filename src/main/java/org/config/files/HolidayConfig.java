package org.config.files;

import org.config.ConfigFile;

import java.io.*;
import java.util.*;

import static org.time.Time.formatDate;
import static org.values.Global.newLine;

public class HolidayConfig extends ConfigFile {
    public HolidayConfig(String fileName) {
        super(fileName);
    }

    public boolean isHolidayDay(Date date) {
        String[] lines = getLines();
        boolean isHoliday = false;
        for (String line : lines) {
            if (line.equals(formatDate(date))) {
                isHoliday = true;
                break;
            }
        }
        return isHoliday;
    }

    public void setHolidaysPeriod(Date dateFrom, Date dateTo) {
        if (dateFrom.after(dateTo)) {
            return;
        }
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFrom);
        while (calendar.getTime().before(dateTo)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        dates.add(calendar.getTime());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigFilePath(), true));
            for (Date date : dates) {
                writer.write(formatDate(date) + newLine);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
