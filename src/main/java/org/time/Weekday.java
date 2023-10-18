package org.time;

public enum Weekday {
    MONDAY("Montag"),
    TUESDAY("Dienstag"),
    WEDNESDAY("Mittwoch"),
    THURSDAY("Donnerstag"),
    FRIDAY("Freitag"),
    SATURDAY("Samstag"),
    SUNDAY("Sonntag");

    private final String weekday;

    Weekday(String day) {
        weekday = day;
    }

    public String getAsString() {
        return weekday;
    }
}
