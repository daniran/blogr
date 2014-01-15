package org.daniran.blogr.tests;

import org.daniran.blogr.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by daniran on 1/15/14.
 */
public abstract class BaseTest {
    protected static final String ts1;
    protected static final String ts2;
    protected static final String ts3;

    static {
        Calendar cal = Calendar.getInstance();

        cal.set(2000, Calendar.JANUARY, 1);
        Date time1 = cal.getTime();
        ts1 = DateUtils.GetDateAsString(time1);

        cal.set(1990, Calendar.JANUARY, 1);
        Date time2 = cal.getTime();
        ts2 = DateUtils.GetDateAsString(time2);

        cal.set(1980, Calendar.JANUARY, 1);
        Date time3 = cal.getTime();
        ts3 = DateUtils.GetDateAsString(time3);

    }
}
