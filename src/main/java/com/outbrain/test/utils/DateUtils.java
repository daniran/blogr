package com.outbrain.test.utils;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by daniran on 1/13/14.
 */
public class DateUtils {
    private final static DateFormatter formatter = new DateFormatter();

    static {
        formatter.setIso(DateTimeFormat.ISO.DATE_TIME);
    }

    public static String GetDateAsString(Date date) {
        final String isoTime = formatter.print(date, Locale.getDefault());

        return isoTime;
    }

    public static Date StringDateToDate(String StrDate) {
        Date dateToReturn = null;

        try {
            dateToReturn = formatter.parse(StrDate, Locale.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateToReturn;
    }
}
