package com.geridea.trentastico.utils.time;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.geridea.trentastico.Config.DATE_TO_FORCE;
import static com.geridea.trentastico.Config.DEBUG_FORCE_ANOTHER_DATE;
import static com.geridea.trentastico.Config.DEBUG_MODE;

/*
 * Created with ♥ by Slava on 12/03/2017.
 */
public class CalendarUtils {

    public static final int SECONDS_MS = 1000;
    public static final int MINUTE_MS  = 60 * SECONDS_MS;
    public static final int HOUR_MS    = 60 * MINUTE_MS;
    public static final int DAY_MS     = 24 * HOUR_MS;

    private final static SimpleDateFormat formatDDMMYY = new SimpleDateFormat("dd MM yyyy", Locale.ITALIAN);
    private final static SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ITALIAN);
    private final static SimpleDateFormat formatEEEEDDMMMM = new SimpleDateFormat("EEEE dd MMMM", Locale.ITALIAN);
    private final static SimpleDateFormat formatHHMM = new SimpleDateFormat("HH:mm", Locale.ITALIAN);

    /**
     * @return the first day of the current week, at 00:00:00.
     */
    @NonNull
    public static Calendar calculateFirstDayOfWeek() {
        return calculateFirstDayOfWeek(getDebuggableToday());
    }

    /**
     * @return the first day of the week containing the specified date, at 00:00:00.
     */
    @NonNull
    public static Calendar calculateFirstDayOfWeek(Calendar date) {
        Calendar firstDayOfWeek = (Calendar) date.clone();
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfWeek.clear(Calendar.MINUTE);
        firstDayOfWeek.clear(Calendar.SECOND);
        firstDayOfWeek.clear(Calendar.MILLISECOND);
        firstDayOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return firstDayOfWeek;
    }

    public static String formatDDMMYY(Calendar calendar) {
        return formatDDMMYY.format(calendar.getTime());
    }

    public static Calendar getPurgedCalendarInstance() {
        Calendar instance = Calendar.getInstance();
        instance.clear();

        return instance;
    }

    public static Calendar getClearCalendar(){
        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        return instance;
    }

    public static Calendar getCalendarInitializedAs(Calendar calendar){
        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        instance.setTime(calendar.getTime());
        return instance;
    }

    public static Calendar getCalendarInitializedAs(long millis){
        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        instance.setTimeInMillis(millis);
        return instance;
    }

    /**
     * @return yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static String formatTimestamp(long millis) {
        return formatTimestamp.format(new Date(millis));
    }

    public static String formatEEEEDDMMMM(long millis) {
        return formatEEEEDDMMMM.format(new Date(millis));
    }

    public static String formatHHMM(long millis) {
        return formatHHMM.format(new Date(millis));
    }

    public static Calendar getDebuggableToday() {
        Calendar aDay = Calendar.getInstance();
        if(DEBUG_MODE && DEBUG_FORCE_ANOTHER_DATE){
            aDay.setTimeInMillis(DATE_TO_FORCE);
        }

        return aDay;
    }

    public static long getDebuggableMillis() {
       return DEBUG_MODE && DEBUG_FORCE_ANOTHER_DATE ? DATE_TO_FORCE : System.currentTimeMillis();
    }

    public static long getMillisWithMinutesDelta(int delta) {
        Calendar now = getDebuggableToday();
        now.add(Calendar.MINUTE, delta);
        return now.getTimeInMillis();
    }

    public static long addMinutes(long millis, int delta) {
        Calendar cal = getCalendarInitializedAs(millis);
        cal.add(Calendar.MINUTE, delta);
        return cal.getTimeInMillis();
    }

    /**
     * Checks if two times are on the same day.
     * @param dayOne The first day.
     * @param dayTwo The second day.
     * @return Whether the times are on the same day.
     */
    public static boolean isSameDay(Calendar dayOne, Calendar dayTwo) {
        return dayOne.get(Calendar.YEAR)        == dayTwo.get(Calendar.YEAR) &&
               dayOne.get(Calendar.DAY_OF_YEAR) == dayTwo.get(Calendar.DAY_OF_YEAR);
    }
}
