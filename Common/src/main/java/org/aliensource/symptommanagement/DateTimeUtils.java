package org.aliensource.symptommanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ttruong on 21-Nov-14.
 */
public final class DateTimeUtils {

    /// date and time
    public static final SimpleDateFormat FORMAT_DDMMYYYY_HHMMSS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static final SimpleDateFormat FORMAT_DDMMYYYY_HHMM = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    //time
    public static final SimpleDateFormat FORMAT_HHMM = new SimpleDateFormat("HH:mm");

    //date
    public static final SimpleDateFormat FORMAT_DDMMYYYY = new SimpleDateFormat("dd.MM.yyyy");

    /////// time manipulation
    public static CharSequence getTimeAsString(int hourOfDay, int minute) {
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    public static CharSequence getTimeAsString(long time) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        int hourOfDay = cal.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = cal.get(GregorianCalendar.MINUTE);
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    // Prepends a "0" to 1-digit minutes
    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static int[] getHourAndMinute(CharSequence timeS) {
        CharSequence hourOfDayS = timeS.subSequence(0, 2);
        CharSequence minuteS = timeS.subSequence(3, 5);

        int hourOfDay = Integer.parseInt(hourOfDayS.toString());
        int minute = Integer.parseInt(minuteS.toString());
        return new int[]{hourOfDay, minute};
    }

    public static long getTime(int hourOfDay, int minute) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(0);
        cal.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        cal.set(GregorianCalendar.MINUTE, minute);
        return cal.getTimeInMillis();
    }

    //// date manipulation
    public static Calendar getDate(String dateS) throws ParseException {
        Date date = FORMAT_DDMMYYYY.parse(dateS);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(date.getTime());
        return cal;
    }

    public static String getDate(int year, int month, int dayOfMonth) throws ParseException {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return FORMAT_DDMMYYYY.format(new Date(cal.getTimeInMillis()));
    }

}
