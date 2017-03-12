package trentastico.geridea.com.trentastico.activities.gui.views;


/*
 * Created with ♥ by Slava on 11/03/2017.
 */

import android.content.Context;
import android.util.AttributeSet;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.threerings.signals.Signal0;
import com.threerings.signals.Signal1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseTimesCalendar extends WeekView implements DateTimeInterpreter, MonthLoader.MonthChangeListener {

    /**
     * Dispatched when the loading of events has been completed and the calendar can be made
     * visible.
     */
    public final Signal1<Boolean> onLoadingOperationFinished = new Signal1<>();

    /**
     * Dispatched when the calendar starts loading something from internet. The argument is that
     * "something".
     */
    public final Signal1<CalendarLoadingOperation> onLoadingOperationStarted = new Signal1<>();

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.ITALIAN);

    public CourseTimesCalendar(Context context) {
        super(context);
        initCalendar();
    }

    public CourseTimesCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCalendar();
    }

    public CourseTimesCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCalendar();
    }

    private void initCalendar() {
        setDateTimeInterpreter(this);
        setMonthChangeListener(this);
    }

    @Override
    public String interpretDate(Calendar date) {
        return DATE_FORMAT.format(date.getTime());
    }

    @Override
    public String interpretTime(int hour) {
        return hour+":00";
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return new ArrayList<WeekViewEvent>();
    }

    public class CalendarLoadingOperation {

    }
}
