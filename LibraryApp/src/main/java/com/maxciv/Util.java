package com.maxciv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Util {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static String getStringFromFormattedDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return DATE_FORMAT.format(date);
        }
    }

    public static String getStringTimeFromFormattedDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return TIME_FORMAT.format(date);
        }
    }

    public static Date getDateFromFormattedString(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            try {
                return DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
