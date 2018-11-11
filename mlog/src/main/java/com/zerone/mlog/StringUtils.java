package com.zerone.mlog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    private static LogUtils log = LogUtils.getInstance("MLog", "StringUtils");

    public StringUtils() {
    }

    public static final String formatDate(long time, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format, Locale.CHINA);
        Date d = new Date(time);
        return formater.format(d);
    }

    public static final long parseDate(String time, String format) {
        if (null != time && null != format) {
            SimpleDateFormat formater = new SimpleDateFormat(format, Locale.CHINA);
            long time2 = 0L;

            try {
                Date date = formater.parse(time);
                time2 = date.getTime();
            } catch (ParseException var7) {
                log.e(var7);
            }

            return time2;
        } else {
            return 0L;
        }
    }
}
