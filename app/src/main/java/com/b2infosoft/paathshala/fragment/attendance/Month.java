package com.b2infosoft.paathshala.fragment.attendance;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.AttendanceCalenderAdapter;
import com.b2infosoft.paathshala.app.Config;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.MonthInfo;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Month extends Fragment implements View.OnClickListener {
    private static final String TAG = Month.class.getName();
    Active active;
    Tags tags;
    private DBHelper dbHelper;
    Network network;
    View view;
    private GridView grid;
    private ProgressDialog progress;
    private ImageButton month_pre, month_next;
    Config config = Config.getInstance();
    TextView month;
    int mMonth = 0;
    int width;
    DisplayMetrics dm = new DisplayMetrics();

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();
    private final String MONTH_NAME[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private final int MONTH_ID[] = {3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2};
    private String SESSION[];
    private TextView present, absent, leave, half_day;

    public Month() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = Active.getInstance(getContext());
        tags = Tags.getInstance();
        network = Network.getInstance(getActivity());
        dbHelper = new DBHelper(getActivity());
        SESSION = active.getValue(tags.SESSION).split("-");
        view = inflater.inflate(R.layout.fragment_month, container, false);
        grid = (GridView) view.findViewById(R.id.gridView);
        month = (TextView) view.findViewById(R.id.current_date);
        month.setOnClickListener(this);
        present = (TextView) view.findViewById(R.id.total_present);
        absent = (TextView) view.findViewById(R.id.total_absent);
        leave = (TextView) view.findViewById(R.id.total_leave);
        half_day = (TextView) view.findViewById(R.id.total_half_day);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = (dm.widthPixels);
/*
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        updateCalendar(events);
*/

        month_pre = (ImageButton) view.findViewById(R.id.month_left);
        month_next = (ImageButton) view.findViewById(R.id.month_right);
        month_pre.setOnClickListener(this);
        month_next.setOnClickListener(this);
        //month.setText(String.valueOf(currentDate.get(Calendar.MONTH)));
        mMonth = currentDate.get(Calendar.MONTH);
        updateDate(mMonth);
        return view;
    }


    private int getItemPosition(int item) {
        for (int i = 0; i < MONTH_ID.length; i++) {
            if (MONTH_ID[i] == item) {
                return i;
            }
        }
        return 0;
    }
    private int getItemPosition(String string){
        for (int i = 0; i < MONTH_NAME.length; i++) {
            if (MONTH_NAME[i].equalsIgnoreCase(string)) {
                return i;
            }
        }
        return 0;
    }
    /**
     * Display dates correctly in grid
     */

    public void updateDate(int mon) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, mon);
        if (mon <= 2) {
            month.setText(MONTH_NAME[mon] + " ".concat(SESSION[1]));
            calendar.set(Calendar.YEAR, Integer.parseInt(SESSION[1]));
            //updateCalendar(null, calendar);
            MonthInfo info = dbHelper.getMonthAttendance(mon + "", SESSION[1]);
            if (info == null) {
                searchYearAttendance(SESSION[1], mon + "");
            } else {
                updateCalendar(info);
            }

        } else {
            month.setText(MONTH_NAME[mon] + " ".concat(SESSION[0]));
            calendar.set(Calendar.YEAR, Integer.parseInt(SESSION[0]));
            //updateCalendar(null, calendar);
            MonthInfo info = dbHelper.getMonthAttendance(mon + "", SESSION[0]);
            if (info == null) {
                searchYearAttendance(SESSION[0], mon + "");
            } else {
                updateCalendar(info);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int position = getItemPosition(mMonth);
        switch (v.getId()) {
            case R.id.month_left:
                if (position <= 11 && position > 0) {
                    mMonth = MONTH_ID[position - 1];
                    updateDate(mMonth);
                }
                break;
            case R.id.month_right:
                if (position < 11 && position >= 0) {
                    mMonth = MONTH_ID[position + 1];
                    updateDate(mMonth);
                }
                break;
            case R.id.current_date:
                chooseMonth();
                break;
        }
    }

    private void chooseMonth() {
        String string[] = {
                MONTH_NAME[3] + " " + SESSION[0],
                MONTH_NAME[4] + " " + SESSION[0],
                MONTH_NAME[5] + " " + SESSION[0],
                MONTH_NAME[6] + " " + SESSION[0],
                MONTH_NAME[7] + " " + SESSION[0],
                MONTH_NAME[8] + " " + SESSION[0],
                MONTH_NAME[9] + " " + SESSION[0],
                MONTH_NAME[10] + " " + SESSION[0],
                MONTH_NAME[11] + " " + SESSION[0],
                MONTH_NAME[0] + " " + SESSION[1],
                MONTH_NAME[1] + " " + SESSION[1],
                MONTH_NAME[2] + " " + SESSION[1]
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SELECT MONTH");
        builder.setSingleChoiceItems(string, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                String string[] = checkedItem.toString().split(" ");
                mMonth = getItemPosition(string[0]);
                updateDate(mMonth);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void updateCalendar(MonthInfo info) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, info.getMonth());
        calendar.set(Calendar.YEAR, info.getYear());

        ArrayList<Date> cells = new ArrayList<>();
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
        grid.setAdapter(new AttendanceCalenderAdapter(getContext(), R.layout.calender, cells, info, mMonth));
        grid.getLayoutParams().height= width-(width/7);
        grid.getLayoutParams().width= width;

        present.setText(String.valueOf(info.getPresent()));
        absent.setText(String.valueOf(info.getAbsent()));
        leave.setText(String.valueOf(info.getLeave()));
        half_day.setText(String.valueOf(info.getHalfDay()));
    }

    /*
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
        grid.setAdapter(new AttendanceCalenderAdapter(getActivity(),R.layout.calender,cells, info));
    }
*/


    private void searchYearAttendance(final String year, final String month_1) {
        if (!network.isInternetAvailable()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        Urls urls = Urls.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.YEAR_YEAR, year);
        map.put(tags.YEAR_MONTH, month_1);

        showProgress();
        String url = urls.getUrl(urls.getPath(tags.MONTH_ATTENDANCE), map);
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        MonthInfo month = new MonthInfo();
                        month.setMonth(Integer.parseInt(month_1));
                        month.setYear(Integer.parseInt(year));
                        try {
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
                                    month.setTSix(object.getString(tags.TSIX));
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
                            dismissProgress();
                        } catch (Exception e) {
                            dismissProgress();
                        }
                        dismissProgress();
                        updateCalendar(month);
                        dbHelper.deleteMonthAttendance(month.getMonth() + "", month.getYear() + "");
                        dbHelper.setMonthAttendance(month);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        dismissProgress();
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
