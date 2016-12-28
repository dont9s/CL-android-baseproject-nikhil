package com.skeleton.util.date;

/**
 * Created by kashish nalwa on 12/16/16.
 */
public class DatePair {
    private final int hour;
    private final int minute;

    DatePair(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
