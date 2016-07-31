package com.b2infosoft.paathshala.fragment.attendance;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.AttendanceCalenderAdapter;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.model.MonthInfo;
import com.b2infosoft.paathshala.model.YearInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Month extends Fragment {
    private static final String TAG = Month.class.getName();
    Active active;
    Tags tags;

    View view;
    private GridView grid;
    private ProgressDialog progress;
    private ImageButton month_pre,month_next;
    TextView current_date;
    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    public Month() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = Active.getInstance(getContext());
        tags = Tags.getInstance();
        view = inflater.inflate(R.layout.fragment_month, container, false);
        grid = (GridView) view.findViewById(R.id.gridView);
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        updateCalendar(events);
        month_pre = (ImageButton)view.findViewById(R.id.month_left);
        month_next = (ImageButton)view.findViewById(R.id.month_right);
        month_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Date> events = new HashSet<>();
                events.add(new Date());
                currentDate.add(Calendar.MONTH, -1);
                //updateCalender(events);
            }
        });

        month_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                //updateCalender(null);
            }
        });
        return view;
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        //grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        grid.setAdapter(new AttendanceCalenderAdapter(getContext(), R.layout.calender, cells, events));

        // update title
        //SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
//        int season = monthSeason[month];
//        int color = rainbow[season];

//        header.setBackgroundColor(getResources().getColor(color));

    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;
        // for view inflation
        private LayoutInflater inflater;
        private MonthInfo info;
        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }
        public CalendarAdapter(Context context, ArrayList<Date> days, MonthInfo info) {
            super(context, R.layout.control_calendar_day, days);
            this.info = info;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view1, ViewGroup parent) {

            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view1 == null)
                view1 = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image
            //view1.setBackgroundResource(0);
            if (eventDays != null) {
                for (Date eventDate : eventDays) {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year) {
                        // mark this day for event
                        view1.setBackgroundResource(R.drawable.reminder);
                        break;
                    }
                }
            }

            // clear styling
            //((TextView) view1).setTypeface(null, Typeface.NORMAL);
            //((TextView) view1).setTextColor(Color.BLACK);
/*
            if (month != today.getMonth() || year != today.getYear())
            {
                // if this day is outside current month, grey it out
                ((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));
            }
            else if (day == today.getDate())
            {
                // if it is today, set it to blue/bold
                ((TextView)view).setTypeface(null, Typeface.BOLD);
                ((TextView)view).setTextColor(getResources().getColor(R.color.today));
            }

  */          // set text
            ((TextView) view1).setText(String.valueOf(day));

            return view1;
        }
    }

    private void updateCalenderAttendance(MonthInfo info){
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        //grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        grid.setAdapter(new AttendanceCalenderAdapter(getActivity(),R.layout.calender,cells, info));

    }

    private void searchYearAttendance(String year, String month) {
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.YEAR_YEAR, year);
        map.put(tags.YEAR_MONTH, month);
        showProgress();
        String url = urls.getUrl(urls.getPath(tags.MONTH_ATTENDANCE), map);
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        try {
                            MonthInfo month = new MonthInfo();
                            if (response.has(tags.ARR_TOTAL_MONTH_ATTENDANCE)) {
                                JSONArray result = response.getJSONArray(tags.ARR_TOTAL_MONTH_ATTENDANCE);
                                JSONObject object = result.getJSONObject(0);
                                if (object.has(tags.ONE)) {
                                    month.setOne(object.getString(tags.ONE));
                                }
                                if (object.has(tags.TWO)) {
                                    month.setTwo(object.getString(tags.TWO));
                                }
                                if (object.has(tags.THREE)) {
                                    month.setThree(object.getString(tags.THREE));
                                }
                                if (object.has(tags.FOUR)) {
                                    month.setFour(object.getString(tags.FOUR));
                                }
                                if (object.has(tags.FIVE)) {
                                    month.setFive(object.getString(tags.FIVE));
                                }
                                if (object.has(tags.SIX)) {
                                    month.setSix(object.getString(tags.SIX));
                                }
                                if (object.has(tags.SEVEN)) {
                                    month.setSeven(object.getString(tags.SEVEN));
                                }
                                if (object.has(tags.EIGHT)) {
                                    month.setEight(object.getString(tags.EIGHT));
                                }
                                if (object.has(tags.NINE)) {
                                    month.setNine(object.getString(tags.NINE));
                                }
                                if (object.has(tags.TEN)) {
                                    month.setTen(object.getString(tags.TEN));
                                }
                                if (object.has(tags.ELEVEN)) {
                                    month.setEleven(object.getString(tags.ELEVEN));
                                }
                                if (object.has(tags.TWELVE)) {
                                    month.setTwelve(object.getString(tags.TWELVE));
                                }
                                if (object.has(tags.THIRTEEN)) {
                                    month.setThirteen(object.getString(tags.THIRTEEN));
                                }
                                if (object.has(tags.FOURTEEN)) {
                                    month.setFourteen(object.getString(tags.FOURTEEN));
                                }
                                if (object.has(tags.FIFTEEN)) {
                                    month.setFifteen(object.getString(tags.FIFTEEN));
                                }
                                if (object.has(tags.SIXTEEN)) {
                                    month.setSixteen(object.getString(tags.SIXTEEN));
                                }
                                if (object.has(tags.SEVENTEEN)) {
                                    month.setSeventeen(object.getString(tags.SEVENTEEN));
                                }
                                if (object.has(tags.EIGHTEEN)) {
                                    month.setEighteen(object.getString(tags.EIGHTEEN));
                                }
                                if (object.has(tags.NINETEEN)) {
                                    month.setNineteen(object.getString(tags.NINETEEN));
                                }
                                if (object.has(tags.TWENTY)) {
                                    month.setTwenty(object.getString(tags.TWENTY));
                                }
                                if (object.has(tags.TONE)) {
                                    month.setTOne(object.getString(tags.TONE));
                                }
                                if (object.has(tags.TTWO)) {
                                    month.setTTwo(object.getString(tags.TTWO));
                                }
                                if (object.has(tags.TThree)) {
                                    month.setTThree(object.getString(tags.TThree));
                                }
                                if (object.has(tags.TFOUR)) {
                                    month.setTFour(object.getString(tags.TFOUR));
                                }
                                if (object.has(tags.TFIVE)) {
                                    month.setTFive(object.getString(tags.TFIVE));
                                }
                                if (object.has(tags.TSIX)) {
                                    month.setTSex(object.getString(tags.TSIX));
                                }
                                if (object.has(tags.TSEVEN)) {
                                    month.setTSeven(object.getString(tags.TSEVEN));
                                }
                                if (object.has(tags.TEIGHT)) {
                                    month.setTEight(object.getString(tags.TEIGHT));
                                }
                                if (object.has(tags.TNINE)) {
                                    month.setTNine(object.getString(tags.TNINE));
                                }
                                if (object.has(tags.TTEN)) {
                                    month.setTTen(object.getString(tags.TTEN));
                                }
                                if (object.has(tags.TELEVEN)) {
                                    month.setTEleven(object.getString(tags.TELEVEN));
                                }
                                if (object.has(tags.MONTH_ABSENT)) {
                                    month.setAbsent(object.getInt(tags.MONTH_ABSENT));
                                }
                                if (object.has(tags.MONTH_PRESENT)) {
                                    month.setPresent(object.getInt(tags.MONTH_PRESENT));
                                }
                                if (object.has(tags.MONTH_HALF_DAY)) {
                                    month.setHalfDay(object.getInt(tags.MONTH_HALF_DAY));
                                }
                                if (object.has(tags.MONTH_LEAVE)) {
                                    month.setLeave(object.getInt(tags.MONTH_LEAVE));
                                }
                            }
                            updateCalenderAttendance(month);
                            dismissProgress();
                        } catch (Exception e) {
                            dismissProgress();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        jsObjRequest.setTag(TAG);
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    private void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

}
